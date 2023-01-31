package com.mashibing.internalcommon.constant;

import lombok.Getter;

@Getter
public enum CommonStatusEnum {
    VERIFICATION_CODE_ERROR(1099,"验证码不正确"),

    USER_NOT_EXISTS(1200,"用户不存在"),

    PRICE_RULE_EMPTY(1300,"计价规则不存在"),

    MAP_DICDISTRICT_ERROR(1400,"请求地图错误"),

    TOKEN_ERROR(1199,"token 错误"),
    SUCCESS(1,"SUCCESS"),
    FAIL(0,"FAIL ")
    ;

    private int code;
    private String value;

    CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
