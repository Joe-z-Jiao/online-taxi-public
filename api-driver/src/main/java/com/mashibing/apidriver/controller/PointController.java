package com.mashibing.apidriver.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/point")
public class PointController {

    @PostMapping("/upload")
    public ResponseResult upload(){

        return null;
    }
}
