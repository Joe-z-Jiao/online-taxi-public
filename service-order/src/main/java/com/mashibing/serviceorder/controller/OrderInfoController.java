package com.mashibing.serviceorder.controller;


import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.serviceorder.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhourj
 * @since 2023-02-20
 */
@RestController
@Slf4j
@RequestMapping("/order-info")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @RequestMapping("/add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest) {
        log.info("service-order" + orderRequest.getAddress());
        return null;
    }



    @RequestMapping("/test")
    public String testMapper(){
        return orderInfoService.testMapper();
    }

}
