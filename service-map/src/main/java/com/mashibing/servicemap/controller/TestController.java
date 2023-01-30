package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.DicDistrict;
import com.mashibing.servicemap.mapper.DicDistrictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/test")
    public String TestController() {
        return "service-map test";
    }

    @Autowired
    DicDistrictMapper dicDistrictMapper;

    @GetMapping("/test-Mappper")
    public String testMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("address_name", "00011");
        List<DicDistrict> dicDistricts = dicDistrictMapper.selectByMap(map);
        System.out.println(dicDistricts);
        return "testmapMapper";
    }
}
