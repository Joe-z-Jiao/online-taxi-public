package com.mashibing.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
@EnableFeignClients
public class ServicePayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServicePayApplication.class,args);
    }

    @PostMapping("/test")
    public String test(){
        System.out.println("支付宝回调啦");
        return "外网穿透测试";
    }

}
