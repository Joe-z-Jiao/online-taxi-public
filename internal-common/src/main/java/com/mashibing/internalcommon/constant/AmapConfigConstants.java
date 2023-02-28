package com.mashibing.internalcommon.constant;

import com.mashibing.internalcommon.response.DirectionResponse;

public class AmapConfigConstants {

    /**
     * 路径规划地址
     */
    public static final String DIRECTION_URL = "https://restapi.amap.com/v3/direction/driving";


    /**
     * 行政区域规划
     */
    public static final  String DISTRICT_URL = "https://restapi.amap.com/v3/config/district";

    /**
     * 服务创建
     */
    public static final String SERVICE_ADD_URL = "https://tsapi.amap.com/v1/track/service/add";

    /**
     * 创建终端
     */
    public static final String TERMINAL_ADD = "https://tsapi.amap.com/v1/track/terminal/add";

    /**
     * 周边搜索
     */
    public static final String TERMINAL_AROUNDSEARCH = "https://tsapi.amap.com/v1/track/terminal/aroundsearch";

    /**
     * 轨迹点的上传
     */
    public static final String POINT_UPLOAD = "https://tsapi.amap.com/v1/track/point/upload";
    /**
     * 创建轨迹
     */
    public static final String TRACK_ADD = "https://tsapi.amap.com/v1/track/trace/add";
    /**
     * 查询轨迹和结果(包括：路程和时长)
     */
    public static final String TERMINAL_TRSEARCH="https://tsapi.amap.com/v1/track/terminal/trsearch";

    /**
     * json key值
     */
    public static final String STATUS = "status";
    /**
     * route key值
     */
    public static final String ROUTE = "route";

    /**
     * paths key 值
     */
    public static final String PATHS = "paths";

    public static final String DISTANCE = "distance";

    public static final String DURATION = "duration";

    public static final String DISTRICTS = "districts";

    public static final String ADCODE = "adcode";

    public static final String NAME = "name";

    public static final String LEVEL = "level";

    public static final String STREET = "street";

}
