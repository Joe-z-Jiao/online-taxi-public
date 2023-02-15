package com.mashibing.apidriver.service;

import com.mashibing.apidriver.remote.ServiceDriverUserClients;
import com.mashibing.apidriver.remote.ServiceMapClient;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ApiDriverPointRequest;
import com.mashibing.internalcommon.request.PointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiDriverPointService {

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private ServiceDriverUserClients serviceDriverUserClients;

    public ResponseResult upload(ApiDriverPointRequest apiDriverPointRequest) {

        //获取carId
        Long carId = apiDriverPointRequest.getCarId();
        //通过 carID获取trid tid
        ResponseResult<Car> carById = serviceDriverUserClients.getCarById(carId);
        Car data = carById.getData();
        String trid = data.getTrid();
        String tid = data.getTid();

        //调用地图上传
        PointRequest pointRequest = new PointRequest();
        pointRequest.setTid(tid);
        pointRequest.setTrid(trid);
        pointRequest.setPoints(apiDriverPointRequest.getPoints());

        return serviceMapClient.upload(pointRequest);

    }
}
