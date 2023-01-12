package com.mashibing.apipassenger.interceptor;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mashibing.internalcommon.constant.TokenConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.utils.JwtUtils;
import com.mashibing.internalcommon.utils.RedisKeyPrefixUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String resultString = "";
        boolean result = true;

        String token = request.getHeader("Authorization");

        TokenResult tokenResult = JwtUtils.checkToken(token);

        if (tokenResult == null ){
            resultString = "token error";
            result = false;
        }else {
            //获取拼接key
            String identity = tokenResult.getIdentity();
            String passengerPhone = tokenResult.getPassengerPhone();
            String tokenKey = RedisKeyPrefixUtils.generotorTokenKey(passengerPhone, identity, TokenConstants.Access_TOKEN_TYPE);
            //从 redis 中获取 token
            String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);
            //校验 redis 中的 token 是否与 token 一致
            if ( StringUtils.isBlank(tokenRedis) || !token.trim().equals(tokenRedis.trim())) {
                resultString = "token error";
                result = false;
            }
        }

        if (!result) {
            PrintWriter printWriter = response.getWriter();
            printWriter.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }


        return result;
    }
}
