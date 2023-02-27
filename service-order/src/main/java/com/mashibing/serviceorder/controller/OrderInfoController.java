package com.mashibing.serviceorder.controller;


import com.mashibing.internalcommon.constant.HeaderParamConstants;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.serviceorder.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping("/order")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @RequestMapping("/add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest, HttpServletRequest httpServletRequest) {
        //测试通过，通过header获取deviceCode
//        String deviceCode = httpServletRequest.getHeader(HeaderParamConstants.DEVICE_CODE);
//        orderRequest.setDeviceCode(deviceCode);
        return orderInfoService.add(orderRequest);
    }

    /**
     * 去接乘客
     * @param orderRequest
     * @return
     */
    @PostMapping("/to-pick-up-passenger")
    public ResponseResult changeStatus(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.toPickUpPassenger(orderRequest);
    }

@PostMapping("/arrived-depature")
    public ResponseResult arrivedDeparture(@RequestBody OrderRequest orderRequest){
        return orderInfoService.arrivedDeparture(orderRequest);
    }

    @PostMapping("/pick_up_passenger")
    public ResponseResult PickUpPassenger(@RequestBody OrderRequest orderRequest){
        return orderInfoService.PickUpPassenger(orderRequest);
    }

    @RequestMapping("/test")
    public String testMapper(){
        return orderInfoService.testMapper();
    }

}
