package com.mashibing.serviceDriverUser.controller;


import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceDriverUser.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhourj
 * @since 2023-02-06
 */
@RestController
public class CarController {
    @Autowired
    CarService carService;
    @PostMapping("/car")
    public ResponseResult addCar(@RequestBody Car car){
        return carService.addCar(car);
    }


    @GetMapping("/car")
    public ResponseResult getCar(Long carId) {
        return carService.getCar(carId);
    }

}
