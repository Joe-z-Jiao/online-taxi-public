package com.mashibing.apipassenger.service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.TokenConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.response.TokenResponse;
import com.mashibing.internalcommon.utils.JwtUtils;
import com.mashibing.internalcommon.utils.RedisKeyPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    public ResponseResult refreshToken(String refreTokenSrc) {
       
        //解析 refreshToken
        TokenResult tokenResult = JwtUtils.checkToken(refreTokenSrc);
        if (tokenResult == null) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        String identity = tokenResult.getIdentity();
        String passengerPhone = tokenResult.getPassengerPhone();
        //生成refresh Token Key
        String refreshTokenKey = RedisKeyPrefixUtils.generotorTokenKey(passengerPhone, identity, TokenConstants.Refresh_TOKEN_TYPE);
        //读取redis中的refreshToken
        String refreshTokenRedis = stringRedisTemplate.opsForValue().get(refreshTokenKey);

        //校验 refreshToken
        if (StringUtils.isBlank(refreshTokenRedis) || !refreTokenSrc.trim().equals(refreshTokenRedis.trim())) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getValue());
         }
        //生成双 Token
        String accessTokenKey = RedisKeyPrefixUtils.generotorTokenKey(passengerPhone, identity, TokenConstants.Access_TOKEN_TYPE);

        String accessToken = JwtUtils.generatorToken(passengerPhone, identity, TokenConstants.Access_TOKEN_TYPE);
        String refreshToken = JwtUtils.generatorToken(passengerPhone, identity, TokenConstants.Refresh_TOKEN_TYPE);

        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,31, TimeUnit.DAYS);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setRefreshToken(refreshToken);
        tokenResponse.setAccessToken(accessToken);

        return ResponseResult.success(tokenResponse);
    }
}
