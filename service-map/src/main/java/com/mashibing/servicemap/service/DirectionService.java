package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DirectionResponse;
import com.mashibing.servicemap.remote.MapDirectionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class DirectionService {

    @Autowired
    private MapDirectionClient mapDirectionClient;


    /**
     * 时长起点和终点的经纬度计算距离与市场
     * @param depLongitude
     * @param depLatitude
     * @param destLatitude
     * @param destLongitude
     * @return
     */
    public ResponseResult driving(String depLongitude,String depLatitude,String destLatitude,String destLongitude){

        //调用第三方服务
        DirectionResponse direction = mapDirectionClient.direction(depLongitude, depLatitude, destLatitude, destLongitude);

        return ResponseResult.success(direction);

    }
}
