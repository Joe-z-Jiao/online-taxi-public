package com.mashibing.serviceprice;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForceCastPriceDTO;
import com.mashibing.internalcommon.response.ForceCastPriceResponse;
import com.mashibing.remote.ServiceMapClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForceCastPriceService {

    @Autowired
    public ServiceMapClients serviceMapClients;

    public ResponseResult forceCastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

        log.info("出发地精度：" + depLongitude);
        log.info("出发地纬度：" + depLatitude);
        log.info("目的地精度：" + destLongitude);
        log.info("目的地纬度：" + destLatitude);

        //调用地图服务，查询距离和时长
        ForceCastPriceDTO forceCastPriceDTO = new ForceCastPriceDTO();
        forceCastPriceDTO.setDepLongitude(depLongitude);
        forceCastPriceDTO.setDepLatitude(depLatitude);
        forceCastPriceDTO.setDestLongitude(destLongitude);
        forceCastPriceDTO.setDestLatitude(destLatitude);
        ResponseResult direction = serviceMapClients.direction(forceCastPriceDTO);

        //读取计价规则

        //根据距离，时长和计价规则，计算价格

        ForceCastPriceResponse forceCastPriceResponse = new ForceCastPriceResponse();
        forceCastPriceResponse.setPrice(12.34);
        return ResponseResult.success(direction);
    }
}
