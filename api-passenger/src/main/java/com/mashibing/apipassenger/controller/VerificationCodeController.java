package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.request.VerificationCodeDTO;
import com.mashibing.apipassenger.service.VerificationCodeService;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;


    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String phoneNum = verificationCodeDTO.getPassengerPhone();

        return verificationCodeService.generatorCode(phoneNum);
    }

    @PostMapping("/verification-code-check")
    public ResponseResult checkVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String verificationCode = verificationCodeDTO.getVerificationCode();
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("手机号为："+ passengerPhone + "验证码为：" + verificationCode);
        return verificationCodeService.checkCode(passengerPhone,verificationCode);
    }

}
