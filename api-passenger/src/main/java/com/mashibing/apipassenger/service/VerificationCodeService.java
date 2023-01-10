package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServiceVerificationCodeClient;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import com.mashibing.internalcommon.response.TokenResponse;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
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

        String key = generatorKeyByPhone(phoneNum);
        //存入redis中
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);
        return ResponseResult.success("");
    }

    /**
     * 根据手机号生成key
     * @param phoneNum
     * @return
     */
    private String generatorKeyByPhone(String phoneNum){
        return verificationCodePrefix + phoneNum;
    }

    /**
     * 校验验证码
     * @param passengerPhone 手机号
     * @param verificationCode 验证码
     * @return
     */
    public ResponseResult checkCode(String passengerPhone,String verificationCode){
        //去redis里面获取手机号和验证码
        //生成key
        String key = generatorKeyByPhone(passengerPhone);
        String redisCode = stringRedisTemplate.opsForValue().get(key);
        System.out.println("通过redis获取到的key" + redisCode);

        //校验验证码
        if (StringUtils.isBlank(redisCode)) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        //传入的验证码与用户输入的验证码不匹配
        if (!verificationCode.trim().equals(redisCode.trim())) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());

        }


        System.out.println("校验手机号和验证码是否匹配");
        System.out.println("判断原来是否有这个用户，并做后续处理");
        System.out.println("颁发令牌");

        //响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken("Token Str");
        return ResponseResult.success(tokenResponse);
    }
}
