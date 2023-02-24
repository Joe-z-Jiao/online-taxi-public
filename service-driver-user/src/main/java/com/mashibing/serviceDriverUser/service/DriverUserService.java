package com.mashibing.serviceDriverUser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.dto.*;
import com.mashibing.internalcommon.response.OrderDriverRespose;
import com.mashibing.serviceDriverUser.mapper.DriverCarBindingRelationshipMapper;
import com.mashibing.serviceDriverUser.mapper.DriverUserMapper;
import com.mashibing.serviceDriverUser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class DriverUserService {

    @Autowired
    DriverUserMapper mapper;

    @Autowired
    DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    @Autowired
    DriverUserMapper driverUserMapper;

    public ResponseResult getDriverUser(){
        DriverUser driverUser = new DriverUser();
        driverUser = mapper.selectById(1);
        return ResponseResult.success(driverUser);
    }

    public ResponseResult addUser(DriverUser driverUser){
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtCreate(now);
        driverUser.setGmtModified(now);
        mapper.insert(driverUser);

        //添加工作状态
        DriverUserWorkStatus driverUserWorkStatus = new DriverUserWorkStatus();
        driverUserWorkStatus.setDriverId(driverUser.getId());
        driverUserWorkStatus.setWorkStatus(DriverCarConstants.DRIVER_WORK_STATUS_STOP);
        driverUserWorkStatus.setGmtCreate(now);
        driverUserWorkStatus.setGmtModified(now);
        driverUserWorkStatusMapper.insert(driverUserWorkStatus);

        return ResponseResult.success("");
    }


    public ResponseResult updateUser(DriverUser driverUser){
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtModified(now);
        mapper.updateById(driverUser);
        return ResponseResult.success("");
    }

    public ResponseResult<DriverUser> getUserByPhone(String driverPhone){
        Map<String, Object> map = new HashMap<>();
        map.put("driver_phone",driverPhone);
        map.put("state", DriverCarConstants.DRIVER_STATE_VALID);
        List<DriverUser> driverUsers = mapper.selectByMap(map);
        if (driverUsers.isEmpty()){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(),CommonStatusEnum.DRIVER_NOT_EXISTS.getValue());
        }
        DriverUser driverUser = driverUsers.get(0);
        return ResponseResult.success(driverUser);
    }

    @Autowired
    DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;
    public ResponseResult<OrderDriverRespose> getAvailableDriver(Long carId){
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id",carId);
        queryWrapper.eq("bind_state",DriverCarConstants.DRIVER_CAR_BIND);
        DriverCarBindingRelationship driverCarBindingRelationship = driverCarBindingRelationshipMapper.selectOne(queryWrapper);
        Long driverId = driverCarBindingRelationship.getDriverId();

        QueryWrapper<DriverUserWorkStatus> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("driver_id",driverId);
        queryWrapper1.eq("work_status",DriverCarConstants.DRIVER_WORK_STATUS_START);

        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatusMapper.selectOne(queryWrapper1);
        if (null == driverUserWorkStatus) {
            return ResponseResult.fail(CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getCode()
                    ,CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getValue());
        } else {
            QueryWrapper<DriverUser> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id",driverId);
            DriverUser driverUser = driverUserMapper.selectOne(queryWrapper2);
            OrderDriverRespose orderDriverRespose = new OrderDriverRespose();
            orderDriverRespose.setCarId(carId);
            orderDriverRespose.setDriverId(driverId);
            orderDriverRespose.setDriverPhone(driverUser.getDriverPhone());
            return ResponseResult.success(orderDriverRespose);
        }
    }
}
