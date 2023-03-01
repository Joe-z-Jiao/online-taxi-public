package com.mashibing.apidriver.remote;

import com.mashibing.internalcommon.dto.*;
import com.mashibing.internalcommon.response.DriverUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClients {

    @RequestMapping(method = RequestMethod.PUT, value = "/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser);

    @RequestMapping(method = RequestMethod.GET, value = "/check-driver/{driverPhone}")
    public ResponseResult<DriverUserResponse> checkDriver(@PathVariable("driverPhone") String driverPhone);

    @RequestMapping(method = RequestMethod.GET, value = "/car")
    public ResponseResult<Car> getCarById(@RequestParam Long carId);

    @RequestMapping(method = RequestMethod.POST,value = "/driver-user-work-status")
    public ResponseResult changeWorkStatus(@RequestBody DriverUserWorkStatus driverUserWorkStatus);

    @GetMapping("/driver-car-binding-relationship")
    public ResponseResult<DriverCarBindingRelationship> getDriverCarBindingRelationship(@RequestParam String driverPhone);
}
