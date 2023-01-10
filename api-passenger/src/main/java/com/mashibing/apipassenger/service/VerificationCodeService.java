package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServiceVerificationCodeClient;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService  {
    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    //key 值前缀
    private String verificationCodePrefix = "passenger-verification-code-";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult generatorCode(String phoneNum){
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationCodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();

        String key = verificationCodePrefix + phoneNum;
        //存入redis中
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);



        return ResponseResult.success("");
    }
}
