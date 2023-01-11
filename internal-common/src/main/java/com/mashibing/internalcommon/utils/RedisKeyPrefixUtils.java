package com.mashibing.internalcommon.utils;

public class RedisKeyPrefixUtils {

    //验证码 key 前缀
    public static String verificationCodePrefix = "passenger-verification-code-";

    //token key 前缀
    public static String tokenPrefix = "token-";

    /**
     * 根据手机号生成key
     * @param phoneNum
     * @return
     */
    public static String generatorKeyByPhone(String phoneNum){
        return verificationCodePrefix + phoneNum;
    }

    /**
     * 根据手机号和身份标识生成token key
     * @param passengerPhone
     * @param identity
     * @return
     */
   public static String generotorTokenKey(String passengerPhone,String identity,String tokenType) {
        return tokenPrefix + passengerPhone + "-" + identity + "-" + tokenType ;
    }
}
