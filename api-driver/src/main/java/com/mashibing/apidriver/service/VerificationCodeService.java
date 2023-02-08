package com.mashibing.apidriver.service;

import com.mashibing.apidriver.remote.ServiceDriverUserClients;
import com.mashibing.apidriver.remote.ServiceVerificationCodeClient;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserResponse;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

@Service
@Slf4j
public class VerificationCodeService {

    @Autowired
    public ServiceDriverUserClients serviceDriverUserClients;

    @Autowired
    public ServiceVerificationCodeClient codeClient;

    public ResponseResult checkAndsendVerificationCode(String driverPhone){
        // 查询service-user是否存在
        ResponseResult<DriverUserResponse> driverUserResponseResponseResult = serviceDriverUserClients.checkDriver(driverPhone);
        DriverUserResponse data = driverUserResponseResponseResult.getData();
        int ifExists = data.getIfExists();
        if (ifExists == DriverCarConstants.DRIVER_NOT_EXISTS) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(),CommonStatusEnum.DRIVER_NOT_EXISTS.getValue(),"");
        }
        log.info(driverPhone + "的司机村存在");
        // 获取验证码
        ResponseResult<NumberCodeResponse> codeResult = codeClient.numberCode(6);
        NumberCodeResponse resultData = codeResult.getData();
        int numberCode = resultData.getNumberCode();
        log.info("验证码" + numberCode);
        //调用第三方发送验证码

        //存入redis
        return ResponseResult.success("");
    }
}
