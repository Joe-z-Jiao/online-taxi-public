package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.ForceCastPriceService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForceCastPriceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ForceCastPriceController {
    @Autowired
    private ForceCastPriceService forceCastPriceService;

    @PostMapping("/forcecast-price")
    public ResponseResult forcecastprice(@RequestBody ForceCastPriceDTO forceCastPriceDTO) {

        String depLongitude = forceCastPriceDTO.getDepLongitude();
        String depLatitude = forceCastPriceDTO.getDepLatitude();
        String destLongitude = forceCastPriceDTO.getDestLongitude();
        String destLatitude = forceCastPriceDTO.getDestLatitude();
        String cityCode = forceCastPriceDTO.getCityCode();
        String vehicleType = forceCastPriceDTO.getVehicleType();
        ResponseResult result = forceCastPriceService.forceCastPrice(depLongitude, depLatitude, destLongitude, destLatitude,cityCode,vehicleType);

        return result;
    }
}
