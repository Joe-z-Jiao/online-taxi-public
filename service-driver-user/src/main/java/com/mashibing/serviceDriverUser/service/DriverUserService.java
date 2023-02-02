package com.mashibing.serviceDriverUser.service;

import com.mashibing.internalcommon.dto.DicDistrict;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceDriverUser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DriverUserService {

    @Autowired
    DriverUserMapper mapper;

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
        return ResponseResult.success("");
    }


    public ResponseResult updateUser(DriverUser driverUser){
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtModified(now);
        mapper.updateById(driverUser);
        return ResponseResult.success("");
    }
}
