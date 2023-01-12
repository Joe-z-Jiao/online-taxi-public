package com.mashibing.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForceCastPriceDTO;
import com.mashibing.serviceprice.ForceCastPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ServicePriceController {

    @Autowired
    private ForceCastPriceService forceCastPriceService;

    @PostMapping("/forcecast-price")
    public ResponseResult forceCastPrice(@RequestBody ForceCastPriceDTO forceCastPriceDTO){
        String depLongitude = forceCastPriceDTO.getDepLongitude();
        String depLatitude = forceCastPriceDTO.getDepLatitude();
        String destLongitude = forceCastPriceDTO.getDestLongitude();
        String destLatitude = forceCastPriceDTO.getDestLatitude();

        ResponseResult result = forceCastPriceService.forceCastPrice(depLongitude, depLatitude, destLongitude, destLatitude);

        return result;
    }
}
