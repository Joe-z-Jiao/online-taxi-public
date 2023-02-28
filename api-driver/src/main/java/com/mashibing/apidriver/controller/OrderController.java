package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.ApiDriverOrderInfoService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    ApiDriverOrderInfoService apiDriverOrderInfoService;

    /**
     * 去接乘客
     * @param orderRequest
     * @return
     */
    @PostMapping("/to-pick-up-passenger")
    public ResponseResult changeStatus(@RequestBody OrderRequest orderRequest) {
        return apiDriverOrderInfoService.toPickUpPassenger(orderRequest);
    }

    /**
     * 司机到达乘客上车点
     * @param orderRequest
     * @return
     */
    @PostMapping("/arrived-depature")
    public ResponseResult arrivedDeparture(@RequestBody OrderRequest orderRequest){
        return apiDriverOrderInfoService.arrivedDeparture(orderRequest);
    }

    /**
     * 司机接到乘客
     * @param orderRequest
     * @return
     */
    @PostMapping("/pick-up-passenger")
    public ResponseResult PickUpPassenger(@RequestBody OrderRequest orderRequest){
        return apiDriverOrderInfoService.PickUpPassenger(orderRequest);
    }

    /**
     * 乘客下车，订单结束
     * @param orderRequest
     * @return
     */
    @PostMapping("/passenger-getoff")
    public ResponseResult passenger_getoff(@RequestBody OrderRequest orderRequest) {
        return apiDriverOrderInfoService.passenger_getoff(orderRequest);
    }
}
