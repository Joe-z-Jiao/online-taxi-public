package com.mashibing.internalcommon.constant;

import lombok.Getter;

@Getter
public enum CommonStatusEnum {
    VERIFICATION_CODE_ERROR(1099,"验证码不正确"),
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
