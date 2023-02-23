package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServicePriceClient;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForceCastPriceDTO;
import com.mashibing.internalcommon.response.ForceCastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForceCastPriceService {

    @Autowired
    private ServicePriceClient servicePriceClient;

    public ResponseResult forceCastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude,String cityCode,String vehicleType) {

        log.info("出发地精度："+ depLongitude);
        log.info("出发地纬度："+ depLatitude);
        log.info("目的地精度："+ destLongitude);
        log.info("目的地纬度："+ destLatitude);

        ForceCastPriceDTO forceCastPriceDTO = new ForceCastPriceDTO();
        forceCastPriceDTO.setDepLongitude(depLongitude);
        forceCastPriceDTO.setDepLatitude(depLatitude);
        forceCastPriceDTO.setDestLongitude(destLongitude);
        forceCastPriceDTO.setDestLatitude(destLatitude);
        forceCastPriceDTO.setCityCode(cityCode);
        forceCastPriceDTO.setVehicleType(vehicleType);
        ResponseResult<ForceCastPriceResponse> forecast = servicePriceClient.forecast(forceCastPriceDTO);

        return ResponseResult.success(forecast.getData());
    }
}
