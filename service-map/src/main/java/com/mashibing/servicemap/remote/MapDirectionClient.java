package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.AmapConfigConstants;
import com.mashibing.internalcommon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MapDirectionClient {

    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;

    public DirectionResponse direction(String depLongitude, String depLatitude, String destLatitude, String destLongitude) {

        //组装调用请求url
        /**
         * key=
         */
        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.DIRECTION_URL);
        url.append("?");
        url.append("origin=" + depLongitude + "," + depLatitude);
        url.append("&");
        url.append("destination=" + destLongitude + "," + destLatitude);
        url.append("&");
        url.append("extensions=base");
        url.append("&");
        url.append("output=json");
        url.append("&");
        url.append("key=");
        url.append(amapKey);
        log.info("url:" + url);

        ResponseEntity<String> directionEntity = restTemplate.getForEntity(url.toString(), String.class);
        log.info("高德地图返回的接口信息：" + directionEntity.getBody());
        String directionString = directionEntity.getBody();

        //解析接口
        DirectionResponse directionResponse = parseDirectionEntity(directionString);

        return directionResponse;
    }

    private DirectionResponse parseDirectionEntity(String directionString) {
        DirectionResponse directionResponse = null;
        try {

            JSONObject result = JSONObject.fromObject(directionString);
            if (result.has(AmapConfigConstants.STATUS)) {
                int status = result.getInt(AmapConfigConstants.STATUS);
                if (status == 1) {
                    if (result.has(AmapConfigConstants.ROUTE)) {
                        JSONObject routeObject = result.getJSONObject(AmapConfigConstants.ROUTE);
                        JSONArray pathArray = routeObject.getJSONArray(AmapConfigConstants.PATHS);
                        JSONObject pathObject = pathArray.getJSONObject(0);
                        directionResponse = new DirectionResponse();
                        if (pathObject.has(AmapConfigConstants.DISTANCE)) {
                            int distance = pathObject.getInt(AmapConfigConstants.DISTANCE);
                            directionResponse.setDistance(distance);
                        }
                        if (pathObject.has(AmapConfigConstants.DURATION)) {
                            int duration = pathObject.getInt(AmapConfigConstants.DURATION);
                            directionResponse.setDuration(duration);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }

        return directionResponse;
    }
}
