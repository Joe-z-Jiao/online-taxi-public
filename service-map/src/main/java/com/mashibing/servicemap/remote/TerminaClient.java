package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.AmapConfigConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TerminaClient {

    @Value("${amap.key}")
    private String amapKey;

    @Value("${amap.sid}")
    private String amapSid;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult<TerminalResponse> add(String name){
        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.TERMINAL_ADD);
        url.append("?");
        url.append("key=" + amapKey);
        url.append("&");
        url.append("sid=" + amapSid);
        url.append("&");
        url.append("name=" + name);

        ResponseEntity<String> forEntity = restTemplate.postForEntity(url.toString(), null, String.class);
        String body = forEntity.getBody();
        JSONObject jsonObject = JSONObject.fromObject(body);
        JSONObject data = jsonObject.getJSONObject("data");
        String tid = data.getString("tid");

        TerminalResponse terminalResponse = new TerminalResponse();
        terminalResponse.setTid(tid);

        return ResponseResult.success(terminalResponse);
    }
}
