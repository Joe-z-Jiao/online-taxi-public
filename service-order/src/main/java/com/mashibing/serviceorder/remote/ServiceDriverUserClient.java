package com.mashibing.serviceorder.remote;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.OrderDriverRespose;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    @RequestMapping(method = RequestMethod.GET,value = "/city-driver/is-available-driver")
    public ResponseResult<Boolean> isAvailableDriver(@RequestParam String cityCode);
    @RequestMapping(method = RequestMethod.GET,value = "/get-available-driver/{carId}")
    public ResponseResult<OrderDriverRespose> getAvailableDriver(@PathVariable("carId") Long carId);

    @RequestMapping(method = RequestMethod.GET,value = "/car")
    public ResponseResult<Car> getCarById(@RequestParam Long carId);
}
