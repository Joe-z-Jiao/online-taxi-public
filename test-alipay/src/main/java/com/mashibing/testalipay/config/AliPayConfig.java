package com.mashibing.testalipay.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@ConfigurationProperties("alipay")
@Data
public class AliPayConfig {
    private String appId;
    private String appPrivatekey;
    private String publicKey;
    private String notifyUrl;

    @PostConstruct
    public void init(){
        Config config = new Config();
        //基础配置
        config.protocol = "https";
        config.gatewayHost = "openapi.alipaydev.com";
        config.signType = "RSA2";
        //业务配置
        config.appId = this.appId;
        config.merchantPrivateKey = this.appPrivatekey;
        config.alipayPublicKey = this.publicKey;
        config.notifyUrl = this.notifyUrl;

        Factory.setOptions(config);

        System.out.println("支付宝配置初始化完成");

    }
}
