package com.mashibing.internalcommon.utils;

public class SsePrefixUtils {

    public static final String sperator = "$";

    public static String generatorSseKey(Long userId,String identity) {
        return userId + sperator + identity;
    }
}
