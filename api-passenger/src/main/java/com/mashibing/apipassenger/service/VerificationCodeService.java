package com.mashibing.apipassenger.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService  {
    public String generatorCode(String phoneNum){
        System.out.println("获取验证码服务");

        String code = "111";

        System.out.println("存入redis里面");

        //返回值
        JSONObject result = new JSONObject();
        result.put("code",1);
        result.put("message","success");
        return result.toString();
    }
}
