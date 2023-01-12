package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DirectionResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class DirectionService {


    /**
     * 时长起点和终点的经纬度计算距离与市场
     * @param depLongitude
     * @param depLatitude
     * @param destLatitude
     * @param destLongitude
     * @return
     */
    public ResponseResult driving(String depLongitude,String depLatitude,String destLatitude,String destLongitude){
        DirectionResponse directionResponse = new DirectionResponse();

        directionResponse.setDistance(123);

        directionResponse.setDuration(12);
        return ResponseResult.success(directionResponse);

    }
}
