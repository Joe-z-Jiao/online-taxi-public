package com.mashibing.internalcommon.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mashibing.internalcommon.dto.TokenResult;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    //盐
    private static final String saults = "!@!#SDasdd4";

    private static final String JWT_KEY_PHONE = "passengerPhone";

    private static final String JWT_KEY_IDENTITY = "identity";

    private static final String JWT_TOKEN_TYPE = "tokenType";

    public static String generatorToken(String passengerPhone,String identity,String tokenType){
        Map<String,String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE,passengerPhone);
        map.put(JWT_KEY_IDENTITY,identity);
        map.put(JWT_TOKEN_TYPE,tokenType);
        //token 过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        Date date = calendar.getTime();

        JWTCreator.Builder builder = JWT.create();

        //整合map
        map.forEach((k,v) -> {
            builder.withClaim(k,v);
        });

        //整合过期时间
//        builder.withExpiresAt(date);

        //加盐
        String sign = builder.sign(Algorithm.HMAC256(saults));

        return sign;
    }

    public static TokenResult parseToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(saults)).build().verify(token);
        String passengerPhone = verify.getClaim(JWT_KEY_PHONE).asString();
        String identity = verify.getClaim(JWT_KEY_IDENTITY).asString();
        TokenResult tokenResult = new TokenResult();
        tokenResult.setPassengerPhone(passengerPhone);
        tokenResult.setIdentity(identity);
        return tokenResult;
    }

    /**
     * 校验 Token 是否正确
     * @param token
     * @return
     */
    public static TokenResult checkToken(String token){
        TokenResult tokenResult = null;
        try {
            tokenResult = JwtUtils.parseToken(token);
        } catch (Exception e) {
        }
        return null;
    }

    public static void main(String[] args) {
        String s = generatorToken("17861403227","1","access");
        System.out.println("生成的 token 为： "+s);

        TokenResult tokenResult = parseToken(s);
        System.out.println("解析的手机号为:" + tokenResult.getPassengerPhone() + "解析的身份认证为：" + tokenResult.getIdentity());
    }
}
