package com.mashibing.serviceorder.service;


import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.serviceorder.mapper.OrderInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhourj
 * @since 2023-02-20
 */
@Service
public class OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    public ResponseResult add(OrderRequest orderRequest){
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderRequest,orderInfo);
        orderInfoMapper.insert(orderInfo);
        return ResponseResult.success("");
    }

    public String testMapper(){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setAddress("110000");
        orderInfoMapper.insert(orderInfo);
        return "";
    }

}
