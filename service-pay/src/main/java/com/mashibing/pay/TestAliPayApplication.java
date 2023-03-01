package com.mashibing.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class TestAliPayApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestAliPayApplication.class,args);
    }

    @PostMapping("/test")
    public String test(){
        System.out.println("支付宝回调啦");
        return "外网穿透测试";
    }

}
