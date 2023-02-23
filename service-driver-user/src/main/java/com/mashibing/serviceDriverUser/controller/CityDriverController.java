package com.mashibing.serviceDriverUser.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceDriverUser.service.CityDriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/city-driver")
public class CityDriverController {
    @Autowired
    CityDriverUserService cityDriverUserService;

    /**
     * 根据城市码查询是否有当前司机
     * @param cityCode
     * @return
     */
    @GetMapping("/is-available-driver")
    public ResponseResult<Boolean> isAvailableDriver(String cityCode) {
        return cityDriverUserService.isAvailableDriver(cityCode);
    }
}
