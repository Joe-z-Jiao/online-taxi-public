package com.mashibing.serviceorder.service;


import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.OrderConstants;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.serviceorder.mapper.OrderInfoMapper;
import com.mashibing.serviceorder.remote.ServicePriceClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhourj
 * @since 2023-02-20
 */
@Service
public class OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private ServicePriceClient servicePriceClient;

    public ResponseResult add(OrderRequest orderRequest){
        //判断计价版本规则是否为最新
        ResponseResult<Boolean> aNew = servicePriceClient.isNew(orderRequest.getFareType(), orderRequest.getFareVersion());
        if (!(aNew.getData())) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_CHANGED.getCode()
                    ,CommonStatusEnum.PRICE_RULE_CHANGED.getValue());
        }
        //创建订单
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderRequest,orderInfo);
        orderInfo.setOrderStatus(OrderConstants.ORDER_START);

        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);
        orderInfoMapper.insert(orderInfo);

        return ResponseResult.success("");
    }

    public String testMapper(){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setAddress("110000");
        orderInfoMapper.insert(orderInfo);
        return "";
    }

}
