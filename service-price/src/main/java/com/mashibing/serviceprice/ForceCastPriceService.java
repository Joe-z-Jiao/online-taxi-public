package com.mashibing.serviceprice;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.ForceCastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForceCastPriceService {
    public ResponseResult forceCastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

        log.info("出发地精度：" + depLongitude);
        log.info("出发地纬度：" + depLatitude);
        log.info("目的地精度：" + destLongitude);
        log.info("目的地纬度：" + destLatitude);

        //调用地图服务，查询距离和时长

        //读取计价规则

        //根据距离，时长和计价规则，计算价格

        ForceCastPriceResponse forceCastPriceResponse = new ForceCastPriceResponse();
        forceCastPriceResponse.setPrice(12.34);
        return ResponseResult.success(forceCastPriceResponse);
    }
}
