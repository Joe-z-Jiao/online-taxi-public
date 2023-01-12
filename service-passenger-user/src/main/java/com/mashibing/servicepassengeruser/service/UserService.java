package com.mashibing.servicepassengeruser.service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.PassengerUser;
import com.mashibing.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;
    public ResponseResult loginOrRegister(String passengerPhone) {
        //根据手机号查询用户信息
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone",passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        //判断用户是否存在
        if (passengerUsers.size() == 0) {
            //若不存在，创建用户
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerName("李四");
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setPassengerGender((byte) 0);
            passengerUser.setGmtModified(LocalDateTime.now());
            passengerUser.setState((byte) 0);
            passengerUser.setGmtCreate(LocalDateTime.now());
            passengerUserMapper.insert(passengerUser);
        }


        return ResponseResult.success();
    }


    public ResponseResult getUserByPhone(String passengerPhone){
        //根据手机号查询用户信息
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone",passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        if (passengerUsers.size() == 0) {
            return ResponseResult.fail(CommonStatusEnum.USER_NOT_EXISTS.getCode(),CommonStatusEnum.USER_NOT_EXISTS.getValue());
        } else {
            PassengerUser passengerUser = passengerUsers.get(0);
            return ResponseResult.success(passengerUser);
        }
    }

}
