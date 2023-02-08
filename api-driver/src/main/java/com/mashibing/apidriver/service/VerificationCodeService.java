package com.mashibing.apidriver.service;

import com.mashibing.apidriver.remote.ServiceDriverUserClients;
import com.mashibing.apidriver.remote.ServiceVerificationCodeClient;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.constant.IdentityConstant;
import com.mashibing.internalcommon.constant.TokenConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.internalcommon.response.DriverUserResponse;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import com.mashibing.internalcommon.response.TokenResponse;
import com.mashibing.internalcommon.utils.JwtUtils;
import com.mashibing.internalcommon.utils.RedisKeyPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class VerificationCodeService {

    @Autowired
    public ServiceDriverUserClients serviceDriverUserClients;

    @Autowired
    public ServiceVerificationCodeClient codeClient;

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    public ResponseResult checkAndsendVerificationCode(String driverPhone) {
        // 查询service-user是否存在
        ResponseResult<DriverUserResponse> driverUserResponseResponseResult = serviceDriverUserClients.checkDriver(driverPhone);
        DriverUserResponse data = driverUserResponseResponseResult.getData();
        int ifExists = data.getIfExists();
        if (ifExists == DriverCarConstants.DRIVER_NOT_EXISTS) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(), CommonStatusEnum.DRIVER_NOT_EXISTS.getValue(), "");
        }
        log.info(driverPhone + "的司机村存在");
        // 获取验证码
        ResponseResult<NumberCodeResponse> codeResult = codeClient.numberCode(6);
        NumberCodeResponse resultData = codeResult.getData();
        int numberCode = resultData.getNumberCode();
        log.info("验证码" + numberCode);
        //调用第三方发送验证码

        //存入redis
        String key = RedisKeyPrefixUtils.generatorKeyByPhone(driverPhone, IdentityConstant.DRIVER_IDENTITY);
        stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);
        return ResponseResult.success("");
    }


    /**
     * 校验验证码
     * @param driverPhone 手机号
     * @param verificationCode 验证码
     * @return
     */
    public ResponseResult checkCode(String driverPhone,String verificationCode){

        //去redis里面获取手机号和验证码
        //生成key
        String key = RedisKeyPrefixUtils.generatorKeyByPhone(driverPhone,IdentityConstant.DRIVER_IDENTITY);
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
        //颁发令牌,identity 不应该魔法值，用常量
        String accessToken = JwtUtils.generatorToken(driverPhone, IdentityConstant.DRIVER_IDENTITY, TokenConstants.Access_TOKEN_TYPE);
        String refreshToken = JwtUtils.generatorToken(driverPhone, IdentityConstant.DRIVER_IDENTITY,TokenConstants.Refresh_TOKEN_TYPE);

        //将token放到redis中
        String accessTokenKey = RedisKeyPrefixUtils.generotorTokenKey(driverPhone, IdentityConstant.DRIVER_IDENTITY,TokenConstants.Access_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30,TimeUnit.DAYS);

        String refreshTokenKey = RedisKeyPrefixUtils.generotorTokenKey(driverPhone, IdentityConstant.DRIVER_IDENTITY,TokenConstants.Refresh_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,31,TimeUnit.DAYS);

        //响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success(tokenResponse);
    }
}
