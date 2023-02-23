package com.mashibing.serviceorder.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-price")
public interface ServicePriceClient {

    @RequestMapping(method = RequestMethod.GET,value = "/price-rule/is-new")
    public ResponseResult<Boolean> isNew(@RequestParam String fareType, @RequestParam Integer fareVersion);
}
