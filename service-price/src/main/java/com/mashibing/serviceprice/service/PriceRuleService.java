package com.mashibing.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceprice.mapper.PriceRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhourj
 * @since 2023-02-23
 */
@Service
public class PriceRuleService {
    @Autowired
    PriceRuleMapper priceRuleMapper;

    public ResponseResult add(PriceRule priceRule){
        //拼接 faretype
        String vehicleType = priceRule.getVehicleType();
        String cityCode = priceRule.getCityCode();
        String fareType = cityCode + "$" +vehicleType;
        priceRule.setFareType(fareType);

        //添加版本号
        //先查询有没有
        //问题 1 添加了版本号，无法确认唯一。使用version最大的来查
        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code",cityCode);
        queryWrapper.eq("vehicle_type",vehicleType);
        queryWrapper.orderByDesc("fare_version");
        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        Integer fareVersion = 0;
        if (priceRules.size() > 0) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EXISTS.getCode(),
                    CommonStatusEnum.PRICE_RULE_EXISTS.getValue());
        }
        priceRule.setFareVersion(++fareVersion);

        priceRuleMapper.insert(priceRule);
        return ResponseResult.success("");
    }

    public ResponseResult edit(PriceRule priceRule){
        //拼接 faretype
        String vehicleType = priceRule.getVehicleType();
        String cityCode = priceRule.getCityCode();
        String fareType = cityCode + "$" +vehicleType;
        priceRule.setFareType(fareType);

        //添加版本号
        //先查询有没有
        //问题 1 添加了版本号，无法确认唯一。使用version最大的来查
        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code",cityCode);
        queryWrapper.eq("vehicle_type",vehicleType);
        queryWrapper.orderByDesc("fare_version");
        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        Integer fareVersion = 0;
        if (priceRules.size() > 0) {
            PriceRule lastedPriceRule = priceRules.get(0);
            Double unitPricePerMinute = lastedPriceRule.getUnitPricePerMinute();
            Double unitPricePerMile = lastedPriceRule.getUnitPricePerMile();
            Integer startMile = lastedPriceRule.getStartMile();
            Double startFare = lastedPriceRule.getStartFare();
            if (unitPricePerMile.doubleValue() == priceRule.getUnitPricePerMile().doubleValue()
            && unitPricePerMinute.doubleValue() == priceRule.getUnitPricePerMinute().doubleValue()
            && startFare.doubleValue() == priceRule.getStartFare().doubleValue()
            && startMile.intValue() == priceRule.getStartMile().intValue()) {
                return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EDITS.getCode(),
                        CommonStatusEnum.PRICE_RULE_NOT_EDITS.getValue());
            }

            fareVersion = lastedPriceRule.getFareVersion();
        }
        priceRule.setFareVersion(++fareVersion);

        priceRuleMapper.insert(priceRule);
        return ResponseResult.success("");
    }

}
