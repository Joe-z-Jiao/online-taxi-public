package com.mashibing.internalcommon.utils;

public class RedisKeyPrefixUtils {

    //验证码 key 前缀
    public static String verificationCodePrefix = "verification-code-";

    //token key 前缀
    public static String tokenPrefix = "token-";

    //黑名单设备号
    public static String blackDeviceCodePrefix = "black-device";

    /**
     * 根据手机号生成key
     * @param phone 手机号
     * @param identity 身份标识
     * @return
     */
    public static String generatorKeyByPhone(String phone,String identity){
        return verificationCodePrefix + identity + "-" + phone;
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
