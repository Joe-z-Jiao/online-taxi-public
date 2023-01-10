package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServiceVerificationCodeClient;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService  {
    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;
    public String generatorCode(String phoneNum){
        System.out.println("获取验证码服务");
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationCodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();
        System.out.println("远程获取的电话号码为:" + numberCode);


        System.out.println("存入redis里面");

        //返回值
        JSONObject result = new JSONObject();
        result.put("code",1);
        result.put("message","success");
        return result.toString();
    }
}
