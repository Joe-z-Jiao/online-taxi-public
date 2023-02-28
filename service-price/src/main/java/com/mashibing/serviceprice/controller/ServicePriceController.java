package com.mashibing.serviceprice.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForceCastPriceDTO;
import com.mashibing.serviceprice.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ServicePriceController {

    @Autowired
    private PriceService forceCastPriceService;

    @PostMapping("/forcecast-price")
    public ResponseResult forceCastPrice(@RequestBody ForceCastPriceDTO forceCastPriceDTO){
        String depLongitude = forceCastPriceDTO.getDepLongitude();
        String depLatitude = forceCastPriceDTO.getDepLatitude();
        String destLongitude = forceCastPriceDTO.getDestLongitude();
        String destLatitude = forceCastPriceDTO.getDestLatitude();
        String cityCode = forceCastPriceDTO.getCityCode();
        String vehicleType = forceCastPriceDTO.getVehicleType();

        ResponseResult result = forceCastPriceService.forceCastPrice(depLongitude, depLatitude, destLongitude, destLatitude,cityCode,vehicleType);

        return result;
    }

@PostMapping("/calculate-price")
    public ResponseResult calculatePrice(@RequestParam Integer distance,@RequestParam Integer duration, @RequestParam String cityCode,@RequestParam String vehicleType){
        return forceCastPriceService.calculatePrice(distance,duration,cityCode,vehicleType);
    }
}
