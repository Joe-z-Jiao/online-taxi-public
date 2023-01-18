package com.mashibing.serviceprice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForceCastPriceDTO;
import com.mashibing.internalcommon.response.DirectionResponse;
import com.mashibing.internalcommon.response.ForceCastPriceResponse;
import com.mashibing.internalcommon.utils.BigDecimalUtils;
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

    /**
     * 计算价格
     * @param distance
     * @param duration
     * @param priceRule
     * @return
     */
    private double getPrice(Integer distance, Integer duration, PriceRule priceRule) {
        //起步价
        double price = 0;
        Double startFare = priceRule.getStartFare();
        price = BigDecimalUtils.add(price,startFare);

        //里程费

        //总里程
        double distanceMile = BigDecimalUtils.devide(distance, 1000);
        //起步里程
        double startMile =(double) priceRule.getStartMile();
        //最后计算的里程
        double distanceSubtract = BigDecimalUtils.substract(distanceMile,startMile);
        double mile = distanceSubtract < 0 ? 0 : distanceSubtract;

        //计程单价 元/km
        double unitPricePerMile = priceRule.getUnitPricePerMile();

        //里程价格
        double mileFare = BigDecimalUtils.multiply(unitPricePerMile,mile);
        price = BigDecimalUtils.add(price,mileFare);

        //时长费
        //时长的分钟数
        double timeMinute = BigDecimalUtils.devide(duration,60);
        //计时单价
        double unitPricePerMinute = priceRule.getUnitPricePerMinute();
        //时长费用
        double timeFare = BigDecimalUtils.multiply(timeMinute,unitPricePerMinute);

        price = BigDecimalUtils.add(price, timeFare);

        BigDecimal priceDecimal = BigDecimal.valueOf(price);

        BigDecimal priceBigDecimal = priceDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return priceBigDecimal.doubleValue();
    }


//    public static void main(String[] args) {
//        PriceRule priceRule = new PriceRule();
//        priceRule.setUnitPricePerMile(1.8);
//        priceRule.setUnitPricePerMinute(0.5);
//        priceRule.setStartFare(10.0);
//        priceRule.setStartMile(3);
//        System.out.println(getPrice(6500, 1800, priceRule));
//    }
}
