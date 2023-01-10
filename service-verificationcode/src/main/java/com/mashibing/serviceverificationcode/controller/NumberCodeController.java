package com.mashibing.serviceverificationcode.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {

    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size){

        //获取验证码
        int resultInt = (int) ((Math.random() * 9 + 1) * Math.pow(10,size - 1));

        NumberCodeResponse response = new NumberCodeResponse();
        response.setNumberCode(resultInt);
        System.out.println("生成的验证码为:" + resultInt);
        return ResponseResult.success(response);
    }

}
