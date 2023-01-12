package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServicePassengerUserClient;
import com.mashibing.internalcommon.dto.PassengerUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private ServicePassengerUserClient userClient;

    public ResponseResult getUser(String accessToken){
        log.info("accessToken :" + accessToken);

        //获取到access Token
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);
        String passengerPhone = tokenResult.getPassengerPhone();
        log.info("手机号为："+ passengerPhone);

        //根据 access Token查询
        ResponseResult<PassengerUser> userByPhone = userClient.getUserByPhone(passengerPhone);

        return ResponseResult.success(userByPhone.getData());
    }
}
