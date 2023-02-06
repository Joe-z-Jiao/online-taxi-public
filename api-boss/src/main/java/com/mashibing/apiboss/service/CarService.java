package com.mashibing.apiboss.service;

import com.mashibing.apiboss.remote.ServiceDriverUserClient;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    @Autowired
    public ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult addCar(Car car) {
        serviceDriverUserClient.addCar(car);
        return ResponseResult.success("");
    }
}
