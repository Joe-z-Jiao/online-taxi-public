package com.mashibing.serviceprice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForceCastPriceDTO;
import com.mashibing.internalcommon.response.DirectionResponse;
import com.mashibing.internalcommon.response.ForceCastPriceResponse;
import com.mashibing.serviceprice.mapper.PriceRuleMapper;
import com.mashibing.serviceprice.remote.ServiceMapClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class ForceCastPriceService {

    @Autowired
    public ServiceMapClients serviceMapClients;

    @Autowired
    public PriceRuleMapper priceRuleMapper;

    ObjectMapper mapper = new ObjectMapper();

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
        ResponseResult<DirectionResponse> direction = serviceMapClients.direction(forceCastPriceDTO);
        log.info("direction:" + direction);
        Integer distance = direction.getData().getDistance();
        Integer duration = direction.getData().getDuration();
        log.info("distance:" + distance);
        log.info("duration:" + duration);

        //读取计价规则
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("city_code", "110000");
        map.put("vehicle_type", "1");
        List<PriceRule> priceResults = priceRuleMapper.selectByMap(map);
        if (priceResults.size() == 0) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(), CommonStatusEnum.PRICE_RULE_EMPTY.getValue());
        }
        PriceRule priceRule = priceResults.get(0);

        //根据距离，时长和计价规则，计算价格
        double price = getPrice(distance, duration, priceRule);

        ForceCastPriceResponse forceCastPriceResponse = new ForceCastPriceResponse();
        forceCastPriceResponse.setPrice(price);
        return ResponseResult.success(forceCastPriceResponse);
    }


    private  double getPrice(Integer distance, Integer duration, PriceRule priceRule) {
        //起步价
        BigDecimal price = new BigDecimal(0);
        Double startFare = priceRule.getStartFare();
        BigDecimal startFareDecimal = new BigDecimal(startFare);
        price = price.add(startFareDecimal);

        //里程费

        //总里程
        BigDecimal distanceDecimal = new BigDecimal(distance);
        BigDecimal distanceMileDecimal = distanceDecimal.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
        //起步里程
        Integer startMile = priceRule.getStartMile();
        BigDecimal startMileDemical = new BigDecimal(startMile);

        //最后计算的里程
        double distanceSubtract = distanceMileDecimal.subtract(startMileDemical).doubleValue();
        Double mile = distanceSubtract < 0 ? 0 : distanceSubtract;
        BigDecimal mileDecimal = new BigDecimal(mile);

        //计程单价 元/km
        Double unitPricePerMile = priceRule.getUnitPricePerMile();
        BigDecimal unitPricePerMileDemical = new BigDecimal(unitPricePerMile);

        //里程价格
        BigDecimal mileFare = mileDecimal.multiply(unitPricePerMileDemical).setScale(2, BigDecimal.ROUND_HALF_UP);
        price = price.add(mileFare);

        //时长费
        BigDecimal time = new BigDecimal(duration);
        //时长的分钟数
        BigDecimal timeDecimal = time.divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_UP);
        //计时单价
        Double unitPricePerMinute = priceRule.getUnitPricePerMinute();
        BigDecimal unitPricePerMinuteDemical = new BigDecimal(unitPricePerMinute);
        //时长费用
        BigDecimal timeFare = timeDecimal.multiply(unitPricePerMinuteDemical);
        price = price.add(timeFare);

        return price.doubleValue();
    }


    /*public static void main(String[] args) {
        PriceRule priceRule = new PriceRule();
        priceRule.setUnitPricePerMile(1.8);
        priceRule.setUnitPricePerMinute(0.5);
        priceRule.setStartFare(10.0);
        priceRule.setStartMile(3);
        System.out.println(getPrice(6500, 1800, priceRule));
    }*/
}
