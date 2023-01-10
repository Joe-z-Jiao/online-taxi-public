package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServiceVerificationCodeClient;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import com.mashibing.internalcommon.response.TokenResponse;
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

    /**
     * 生成验证码
     * @param phoneNum
     * @return
     */
    public ResponseResult generatorCode(String phoneNum){
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationCodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();

        String key = verificationCodePrefix + phoneNum;
        //存入redis中
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);
        return ResponseResult.success("");
    }

    /**
     * 校验验证码
     * @param passengerPhone 手机号
     * @param verificationCode 验证码
     * @return
     */
    public ResponseResult checkCode(String passengerPhone,String verificationCode){
        System.out.println("去redis里面获取手机号和验证码");
        System.out.println("校验手机号和验证码是否匹配");
        System.out.println("判断原来是否有这个用户，并做后续处理");
        System.out.println("颁发令牌");

        //响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken("Token Str");
        return ResponseResult.success(tokenResponse);
    }
}
