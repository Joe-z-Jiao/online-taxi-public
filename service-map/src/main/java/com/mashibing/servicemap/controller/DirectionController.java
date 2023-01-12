package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForceCastPriceDTO;
import com.mashibing.servicemap.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/direction")
public class DirectionController {

    @Autowired
    private DirectionService directionService;

    @GetMapping("/driving")
    public ResponseResult driving(@RequestBody ForceCastPriceDTO forceCastPriceDTO) {
        String depLongitude = forceCastPriceDTO.getDepLongitude();
        String depLatitude = forceCastPriceDTO.getDepLatitude();
        String destLatitude = forceCastPriceDTO.getDestLatitude();
        String destLongitude = forceCastPriceDTO.getDestLongitude();

        ResponseResult driving = directionService.driving(depLongitude, depLatitude, destLatitude, destLongitude);


        return driving;
    }

}
