package com.mashibing.internalcommon.constant;

import lombok.Getter;

@Getter
public enum CommonStatusEnum {
    VERIFICATION_CODE_ERROR(1099,"验证码不正确"),

    USER_NOT_EXISTS(1200,"用户不存在"),

    PRICE_RULE_EMPTY(1300,"计价规则不存在"),

    PRICE_RULE_EXISTS(1301,"计价规则已存在，不允许添加"),

    PRICE_RULE_NOT_EDITS(1302,"计价规则没有变化"),

    PRICE_RULE_CHANGED(1303,"计价规则有变化"),

    MAP_DICDISTRICT_ERROR(1400,"请求地图错误"),

    DRIVER_CAR_BIND_NOT_EXISTS(1500,"司机和车辆绑定关系不存在"),

    DRIVER_NOT_EXISTS(1501,"司机不存在"),

    DRIVER_CAR_BIND_EXISTS(1502,"司机和车辆绑定关系已存在，请勿重复绑定"),

    DRIVER_BIND_EXISTS(1503,"司机已经被绑定了，请勿重复绑定"),

    CAR_BIND_EXISTS(1504,"车辆已经被绑定，请勿重复绑定"),

    TOKEN_ERROR(1199,"token 错误"),

    ORDER_GOING_ON(1600,"有正在进行的订单"),

    DEVICE_IS_BLACK(1601,"该设备超过下单次数"),
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
