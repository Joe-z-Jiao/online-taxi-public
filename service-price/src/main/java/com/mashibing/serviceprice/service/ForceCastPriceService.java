package com.mashibing.serviceprice.service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForceCastPriceDTO;
import com.mashibing.internalcommon.response.ForceCastPriceResponse;
import com.mashibing.serviceprice.mapper.PriceRuleMapper;
import com.mashibing.serviceprice.remote.ServiceMapClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class ForceCastPriceService {

    @Autowired
    public ServiceMapClients serviceMapClients;

    @Autowired
    public PriceRuleMapper priceRuleMapper;

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
        Map<String, Object> map = new HashMap<>();
        map.put("city_code","110000");
        map.put("vehicle_type","1");
        List<PriceRule> priceResults = priceRuleMapper.selectByMap(map);
        if (priceResults.size() == 0) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(),CommonStatusEnum.PRICE_RULE_EMPTY.getValue() );
        }
        PriceRule priceRule = priceResults.get(0);

        //根据距离，时长和计价规则，计算价格

        ForceCastPriceResponse forceCastPriceResponse = new ForceCastPriceResponse();
        forceCastPriceResponse.setPrice(12.34);
        return ResponseResult.success(direction);
    }
}
