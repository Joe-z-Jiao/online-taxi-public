package com.mashibing.apidriver.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {

    public ResponseResult checkAndsendVerificationCode(String driverPhone){
        // 查询service-user是否存在

        // 获取验证码

        //调用第三方发送验证码

        //存入redis
        return ResponseResult.success("");
    }
}
