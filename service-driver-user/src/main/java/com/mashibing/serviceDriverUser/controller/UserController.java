package com.mashibing.serviceDriverUser.controller;

import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserResponse;
import com.mashibing.serviceDriverUser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event;

@RestController
public class UserController {

    @Autowired
    DriverUserService driverUserService;

    /**
     * 新增司机
     *
     * @param driverUser
     * @return
     */
    @PostMapping("/user")
    public ResponseResult addUer(@RequestBody DriverUser driverUser) {
        return driverUserService.addUser(driverUser);
    }

    /**
     * 修改司机
     *
     * @param driverUser
     * @return
     */
    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser) {
        return driverUserService.updateUser(driverUser);
    }

    /**
     * 根据手机号查询司机
     * @param driverPhone
     * @return
     */
    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult<DriverUserResponse> getUser(@PathVariable("driverPhone") String driverPhone) {
        ResponseResult<DriverUser> userByPhone = driverUserService.getUserByPhone(driverPhone);
        DriverUser driverUserDB = userByPhone.getData();
        DriverUserResponse driverUserResponse = new DriverUserResponse();
        int ifExits = DriverCarConstants.DRIVER_EXISTS;
        if ( driverUserDB == null ) {
            ifExits = DriverCarConstants.DRIVER_NOT_EXISTS;
            driverUserResponse.setDriverPhone(driverPhone);
            driverUserResponse.setIfExists(ifExits);
        } else {
            driverUserResponse.setDriverPhone(driverUserDB.getDriverPhone());
            driverUserResponse.setIfExists(ifExits);
        }


        return ResponseResult.success(driverUserResponse);
    }
}
