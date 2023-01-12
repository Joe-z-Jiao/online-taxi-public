package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.UserService;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("users")
    public ResponseResult getUser(HttpServletRequest request){

        //获取到access Token

        //根据 access Token查询
        ResponseResult user = userService.getUser(request.getHeader("Authorization"));


        return user;
    }
}
