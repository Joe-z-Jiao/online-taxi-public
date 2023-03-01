package com.mashibing.servicessepush.controller;

import com.mashibing.internalcommon.utils.SsePrefixUtils;
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
     * 建立链接
     * @param userId 用户
     * @param identity 身份
     * @return
     */
    @GetMapping("/connect")
    public SseEmitter connect(@RequestParam Long userId,@RequestParam String identity) {
        System.out.println("用户 ID：" + userId + "身份Id: " + identity);
        SseEmitter sseEmitter = new SseEmitter(0L);
        String sseMapKey = SsePrefixUtils.generatorSseKey(userId,identity);
        stringSseEmitterMap.put(sseMapKey, sseEmitter);
        return sseEmitter;
    }

    /**
     * 给用户发送消息
     * @param userId
     * @param identity
     * @param content
     * @return
     */
    @GetMapping("/push")
    public String push(@RequestParam Long userId,@RequestParam String identity, @RequestParam String content) {
        String sseMapKey = SsePrefixUtils.generatorSseKey(userId,identity);
        try {
            if (stringSseEmitterMap.containsKey(sseMapKey)) {
                stringSseEmitterMap.get(sseMapKey).send(content);
            } else {
                return "推送失败";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "给用户：" + sseMapKey + ",发送了消息：" + content;
    }

    /**
     * 关闭连接
     * @param userId
     * @param identity
     * @return
     */
    @GetMapping("/close/{driverId}")
    public String close(@RequestParam Long userId,@RequestParam String identity){
        String sseMapKey = SsePrefixUtils.generatorSseKey(userId,identity);
        log.info("关闭连接：" + sseMapKey);
        if (stringSseEmitterMap.containsKey(sseMapKey)) {
            stringSseEmitterMap.remove(sseMapKey);
        }

        return "close 成功";
    }
}
