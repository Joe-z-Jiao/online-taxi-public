package com.mashibing.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForceCastPriceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-map")
public interface ServiceMapClients {
    @RequestMapping(method = RequestMethod.GET,value = "/direction/driving")
    public ResponseResult direction(@RequestBody ForceCastPriceDTO forceCastPriceDTO);
}
