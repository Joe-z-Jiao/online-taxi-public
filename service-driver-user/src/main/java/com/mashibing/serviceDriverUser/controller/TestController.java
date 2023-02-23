package com.mashibing.serviceDriverUser.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceDriverUser.mapper.DriverUserMapper;
import com.mashibing.serviceDriverUser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    public DriverUserService driverUserService;

    @GetMapping("/test")
    public String testController(){
        return "Service-Driver-User Test";
    }

    @GetMapping("test-db")
    public ResponseResult testDb(){
        return driverUserService.getDriverUser();
    }

    @Autowired
    public DriverUserMapper driverUserMapper;
    @GetMapping("/test-xml")
    public int testXml(String cityCode){
        int i = driverUserMapper.select1(cityCode);
        return i;
    }
}
