package com.mashibing.serviceorder.controller;

import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.serviceorder.mapper.OrderInfoMapper;
import com.mashibing.serviceorder.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "service-order test";
    }


    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    OrderInfoService orderInfoService;

    @GetMapping("/test-real-time-order/{orderId}")
    public String dispatchRealTimeOrder(@PathVariable("orderId") long orderId) {
        System.out.println("并发测试：orderId："+ orderId);
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        orderInfoService.dispatchRealTimeOrder(orderInfo);
        return "test-real-time-order success";
    }
}
