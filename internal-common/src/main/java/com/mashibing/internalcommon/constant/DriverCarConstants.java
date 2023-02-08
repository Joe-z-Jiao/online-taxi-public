package com.mashibing.internalcommon.constant;

public class DriverCarConstants {
    /**
     * 司机与车辆绑定
     */
    public static int DRIVER_CAR_BIND = 1;

    /**
     * 司机与车辆解绑
     */
    public static int DRIVER_CAR_UNBIND = 2;

    /**
     * 司机状态 1：有效
     */
    public static int DRIVER_STATE_VALID = 1;

    /**
     * 司机状态 2：无效
     */
    public static int DRIVER_STATE_INVALID = 0;

    /**
     * 司机存在：1
     */
    public static int DRIVER_EXISTS = 1;

    /**
     * 司机不存在：0
     */
    public static int DRIVER_NOT_EXISTS = 0;

    /**
     * 司机状态：收车
     */
    public static int DRIVER_WORK_STATUS_STOP = 0;

    /**
     * 司机状态：出车
     */
    public static int DRIVER_WORK_STATUS_START = 1;

    /**
     * 司机状态：暂停接单
     */
    public static int DRIVER_WORK_STATUS_SUSPEND = 2;

}
