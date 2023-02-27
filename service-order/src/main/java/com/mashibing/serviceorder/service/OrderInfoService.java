package com.mashibing.serviceorder.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.OrderConstants;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.internalcommon.request.PriceRuleIsNewRequest;
import com.mashibing.internalcommon.response.OrderDriverRespose;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.utils.RedisKeyPrefixUtils;
import com.mashibing.serviceorder.mapper.OrderInfoMapper;
import com.mashibing.serviceorder.remote.ServiceMapClient;
import com.mashibing.serviceorder.remote.ServicePriceClient;
import com.mashibing.serviceorder.remote.ServiceDriverUserClient;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhourj
 * @since 2023-02-20
 */
@Service
@Slf4j
public class OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private ServicePriceClient servicePriceClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult add(OrderRequest orderRequest) {
        //测试当前城市是否有司机
        ResponseResult<Boolean> availableDriver = serviceDriverUserClient.isAvailableDriver(orderRequest.getAddress());
        log.info("测试城市是否有司机结果：" + availableDriver.getData());
        if (!availableDriver.getData()) {
            return ResponseResult.fail(CommonStatusEnum.CITY_DRIVER_EMPTY.getCode(), CommonStatusEnum.CITY_DRIVER_EMPTY.getValue(), "");
        }

        PriceRuleIsNewRequest priceRuleIsNewRequest = new PriceRuleIsNewRequest();
        priceRuleIsNewRequest.setFareType(orderRequest.getFareType());
        priceRuleIsNewRequest.setFareVersion(orderRequest.getFareVersion());
        //判断计价版本规则是否为最新
        ResponseResult<Boolean> aNew = servicePriceClient.isNew(priceRuleIsNewRequest);
        if (!(aNew.getData())) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_CHANGED.getCode()
                    , CommonStatusEnum.PRICE_RULE_CHANGED.getValue(), "");
        }

        //需要判断是否为黑名单用户
        if (isBlackDevice(orderRequest)) {
            return ResponseResult.fail(CommonStatusEnum.DEVICE_IS_BLACK.getCode(), CommonStatusEnum.DEVICE_IS_BLACK.getValue(), "");
        }
        //判断下单的城市和计价规则是否正常
        if (!isPriceRuleExists(orderRequest)) {
            return ResponseResult.fail(CommonStatusEnum.CITY_SERVICE_NOT_SERVICE.getCode(), CommonStatusEnum.CITY_SERVICE_NOT_SERVICE.getValue());
        }

        //判断乘客是否有正在进行的订单
        if (isPassengerOrderGoingOn(orderRequest.getPassengerId()) > 0) {
            return ResponseResult.fail(CommonStatusEnum.ORDER_GOING_ON.getCode(),
                    CommonStatusEnum.ORDER_GOING_ON.getValue(), "");
        }

        //创建订单
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderRequest, orderInfo);
        orderInfo.setOrderStatus(OrderConstants.ORDER_START);

        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);
        orderInfoMapper.insert(orderInfo);
        //派单
        dispatchRealTimeOrder(orderInfo);

        return ResponseResult.success("");
    }

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 实时订单派单逻辑
     *
     * @param orderInfo
     */
    public synchronized void dispatchRealTimeOrder(OrderInfo orderInfo) {
        String depLatitude = orderInfo.getDepLatitude();
        String depLongitude = orderInfo.getDepLongitude();
        String center = depLatitude + "," + depLongitude;
        ResponseResult<List<TerminalResponse>> listResponseResult = null;
        List<Integer> radiusList = new ArrayList<>();
        radiusList.add(2000);
        radiusList.add(4000);
        radiusList.add(5000);
        //搜索结果
        //goto 为了测试
        radius:
        for (int i = 0; i < radiusList.size(); i++) {
            Integer radius = radiusList.get(i);
            listResponseResult = serviceMapClient.terminalAroundSearch(center, radius);

            log.info("在半径为" + radius + "的范围内，寻找车辆" + "结果为："
                    + JSONArray.fromObject(listResponseResult.getData()).toString());
            // 获得终端 [{"carId":1626467262537736194,"tid":"636564420"}]

            // 解析终端
            List<TerminalResponse> data = listResponseResult.getData();
            for (int j = 0; j < data.size(); j++) {
                TerminalResponse terminalResponse = data.get(j);
                Long carId = terminalResponse.getCarId();
                String longitude = terminalResponse.getLongitude();
                String latitude = terminalResponse.getLatitude();
                //查询是否有可派单的司机
                ResponseResult<OrderDriverRespose> availableDriver = serviceDriverUserClient.getAvailableDriver(carId);
                if (availableDriver.getCode() == CommonStatusEnum.CITY_DRIVER_EMPTY.getCode()) {
                    log.info("没有车辆 ID" + carId + "对应的司机");
                    continue radius;
                } else {
                    log.info("车辆 ID：" + carId + "找到了正在出车的司机");
                    OrderDriverRespose orderDriverRespose = availableDriver.getData();
                    Long driverId = orderDriverRespose.getDriverId();
                    String driverPhone = orderDriverRespose.getDriverPhone();
                    String licenseId = orderDriverRespose.getLicenseId();
                    String vehicleNo = orderDriverRespose.getVehicleNo();

                    String lockKey = (driverId + "").intern();
                    RLock lock = redissonClient.getLock(lockKey);
                    lock.lock();
                    //判断司机是否有正在进行的订单
                    if (isDriverOrderGoingOn(driverId) > 0) {
                        lock.unlock();
                        continue;
                    }
                    //订单直接匹配司机
                    orderInfo.setDriverId(driverId);
                    orderInfo.setDriverPhone(driverPhone);
                    orderInfo.setCarId(carId);
                    //从地图中来
                    orderInfo.setReceiveOrderCarLongitude(longitude);
                    orderInfo.setReceiveOrderCarLatitude(latitude);
                    orderInfo.setReceiveOrderTime(LocalDateTime.now());
                    orderInfo.setLicenseId(licenseId);
                    orderInfo.setVehicleNo(vehicleNo);
                    orderInfo.setOrderStatus(OrderConstants.DRIVER_RECEIVE_ORDER);
                    orderInfoMapper.updateById(orderInfo);
                    lock.unlock();
                    //退出，不再进行司机的查找
                    break radius;



                }
            }

            // 根据解析出来的终端，查询车辆信息

            // 找到符合的车辆，进行派单

            // 如果派单成功，则退出循环
        }

    }

    /**
     * 判断是否有计价规则
     *
     * @param orderRequest
     * @return
     */
    private boolean isPriceRuleExists(OrderRequest orderRequest) {
        String fareType = orderRequest.getFareType();
        int index = fareType.indexOf("$");
        String cityCode = fareType.substring(0, index);
        String vehicleType = fareType.substring(index + 1);
        PriceRule priceRule = new PriceRule();
        priceRule.setCityCode(cityCode);
        priceRule.setVehicleType(vehicleType);

        ResponseResult<Boolean> booleanResponseResult = servicePriceClient.ifPriceExists(priceRule);
        return booleanResponseResult.getData();
    }

    /**
     * 判断是否为黑名单用户
     *
     * @param orderRequest
     * @return
     */
    private boolean isBlackDevice(OrderRequest orderRequest) {
        String deviceCode = orderRequest.getDeviceCode();
        //生成key
        String deviceCodeKey = RedisKeyPrefixUtils.blackDeviceCodePrefix + deviceCode;
        //设置 key
        Boolean aBoolean = stringRedisTemplate.hasKey(deviceCodeKey);
        if (aBoolean) {
            String s = stringRedisTemplate.opsForValue().get(deviceCodeKey);
            int i = Integer.parseInt(s);
            if (i >= 2) {
                return true;
            } else {
                stringRedisTemplate.opsForValue().increment(deviceCodeKey);
            }
        } else {
            stringRedisTemplate.opsForValue().set(deviceCodeKey, "1", 1L, TimeUnit.HOURS);
        }
        return false;
    }

    public String testMapper() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setAddress("110000");
        orderInfoMapper.insert(orderInfo);
        return "";
    }

    /**
     * 判断是否有乘客中的订单
     *
     * @param passengerId
     * @return
     */
    private Integer isPassengerOrderGoingOn(Long passengerId) {
        // 判断有正在进行的订单不允许下单
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("passenger_id", passengerId);
        queryWrapper.and(wrapper -> wrapper.eq("order_status", OrderConstants.ORDER_START)
                .or().eq("order_status", OrderConstants.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status", OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstants.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status", OrderConstants.PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstants.PASSENGER_GETOFF)
                .or().eq("order_status", OrderConstants.TO_START_PAY)
        );
        return orderInfoMapper.selectCount(queryWrapper);
    }


    /**
     * 判断是否有司机中的订单
     *
     * @param driverId
     * @return
     */
    private Integer isDriverOrderGoingOn(Long driverId) {
        // 判断有正在进行的订单不允许下单
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverId);
        queryWrapper.and(wrapper -> wrapper
                .eq("order_status", OrderConstants.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status", OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstants.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status", OrderConstants.PICK_UP_PASSENGER)
        );


        Integer integer = orderInfoMapper.selectCount(queryWrapper);
        log.info("司机id" + driverId + ",正在进行的订单的数量：" + integer);
        return integer;
    }

}
