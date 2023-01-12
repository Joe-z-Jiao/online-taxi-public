package com.mashibing.servicepassengeruser.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO){

        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("用户手机号为：" + passengerPhone);
        return userService.loginOrRegister(passengerPhone);

    }

    @GetMapping("/user/{passengerPhone}")
    public ResponseResult getUser(@PathVariable("passengerPhone") String passengerPhone){
        return userService.getUserByPhone(passengerPhone);
    }

}
