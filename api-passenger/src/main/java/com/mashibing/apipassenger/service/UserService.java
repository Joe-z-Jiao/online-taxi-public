package com.mashibing.apipassenger.service;

import com.mashibing.internalcommon.dto.PassengerUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    public ResponseResult getUser(String accessToken){
        log.info("accessToken :" + accessToken);
        PassengerUser user = new PassengerUser();
        user.setPassengerName("李四");
        user.setProfilePhoto("头像");

        return ResponseResult.success(user);
    }
}
