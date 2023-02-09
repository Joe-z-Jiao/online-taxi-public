package com.mashibing.serviceDriverUser.service;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.serviceDriverUser.mapper.CarMapper;
import com.mashibing.serviceDriverUser.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CarService {

    @Autowired
    CarMapper carMapper;

    @Autowired
    ServiceMapClient serviceMapClient;

    public ResponseResult addCar(Car car){
        LocalDateTime now = LocalDateTime.now();
        car.setGmtCreate(now);
        car.setGmtModified(now);

        //获得此车辆对应的 tid
        ResponseResult<TerminalResponse> result = serviceMapClient.addTerminal(car.getVehicleNo());
        String tid = result.getData().getTid();
        car.setTid(tid);
        carMapper.insert(car);
        return ResponseResult.success("");
    }
}
