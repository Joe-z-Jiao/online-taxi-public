package com.mashibing.servicepassengeruser.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.servicepassengeruser.dto.PassengerUser;
import com.mashibing.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;
    public ResponseResult loginOrRegister(String passengerPhone) {
        System.out.println("user service被调用手机号为：" + passengerPhone);
        //根据手机号查询用户信息
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone",passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        System.out.println(passengerUsers.size() == 0?"无记录":passengerUsers.get(0).getPassengerPhone());
        //判断用户是否存在

        //若不存在，创建用户
        return ResponseResult.success();
    }

}
