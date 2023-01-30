package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.constant.AmapConfigConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DicDistrictService {
    @Value("${amap.key}")
    private String amapKey;
    public ResponseResult initDicDistict(String keywords){



        //https://restapi.amap.com/v3/config/district?keywords=北京&subdistrict=2&key=<用户的key>
        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.DISTRICT_URL);
        url.append("?");
        url.append("keywords=" + keywords);
        url.append("&");
        url.append("subdistrict=3");
        url.append("&");
        url.append("key=" + amapKey);
        return ResponseResult.success();
    }


}
