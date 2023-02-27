package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.remote.ServiceOrderClient;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test api passenger";
    }

    /**
     * 有 token 返回
     * @return
     */
    @GetMapping("/authTest")
    public ResponseResult authTest() {
        return ResponseResult.success("auth test");
    }

    /**
     * 没有token也能返回
     * @return
     */
    @GetMapping("/noAuthTest")
    public ResponseResult noAuthTest() {
        return ResponseResult.success("no auth test");
    }

    @Autowired
    private ServiceOrderClient serviceOrderClient;
    @GetMapping("/test-real-time-order/{orderId}")
    public String dispatchRealTimeOrder(@PathVariable("orderId") long orderId) {
        System.out.println("并发测试：api-passenger："+ orderId);
        serviceOrderClient.dispatchRealTimeOrder(orderId);
        return "test-real-time-order success";
    }
}
