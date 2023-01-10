package com.mashibing.internalcommon.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    //盐
    private static final String saults = "!@!#SDasdd4";

    private static final String Jwt_Key = "passengerPhone";

    private static String generatorToken(String passengerPhone){
        Map<String,String> map = new HashMap<>();
        map.put(Jwt_Key,passengerPhone);
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
        builder.withExpiresAt(date);

        //加盐
        String sign = builder.sign(Algorithm.HMAC256(saults));

        return sign;
    }

    private static String parseToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(saults)).build().verify(token);
        Claim claim = verify.getClaim(Jwt_Key);
        return claim.toString();
    }

    public static void main(String[] args) {
        String s = generatorToken("17861403227");
        System.out.println("生成的 token 为： "+s);

        String s1 = parseToken(s);
        System.out.println("解析的数据:" + s1);
    }
}
