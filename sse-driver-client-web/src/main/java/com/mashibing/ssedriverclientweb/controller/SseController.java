package com.mashibing.ssedriverclientweb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class SseController {

    public static Map<String, SseEmitter> stringSseEmitterMap = new HashMap<>();

    /**
     * 建立连接
     * @param driverId
     * @return
     */
    @GetMapping("/connect/{driverId}")
    public SseEmitter connect(@PathVariable("driverId") String driverId) {
        log.info("司机 ID：" + driverId);
        SseEmitter sseEmitter = new SseEmitter(0L);
        stringSseEmitterMap.put(driverId, sseEmitter);
        return sseEmitter;
    }

    /**
     * 推送消息
     * @param driverId
     * @param content
     * @return
     */
    @GetMapping("/push")
    public String push(@RequestParam String driverId, @RequestParam String content) {

        try {
            if (stringSseEmitterMap.containsKey(driverId)) {
                stringSseEmitterMap.get(driverId).send(content);
            } else {
                return "推送失败";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "给用户：" + driverId + ",发送了消息：" + content;
    }

    /**
     * 关闭连接
     * @param driverId
     * @return
     */
    @GetMapping("/close/{driverId}")
    public String close(@PathVariable String driverId){
        log.info("关闭连接：" + driverId);
        if (stringSseEmitterMap.containsKey(driverId)) {
            stringSseEmitterMap.remove(driverId);
        }

        return "close 成功";
    }
}
