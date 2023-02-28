package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrsearchResponse;
import com.mashibing.servicemap.remote.TerminaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public class TerminalService {
    @Autowired
    private TerminaClient terminaClient;

    public ResponseResult<TerminalResponse> add(String name,String desc) {
        return terminaClient.add(name,desc);
    }

    public ResponseResult<List<TerminalResponse>> aroundSearch(String center, Integer radius){

        return terminaClient.aroundsearch(center,radius);
    }

    public ResponseResult<TrsearchResponse> trsearch(String tid, Long starttime, Long endtime) {
        return terminaClient.trsearch(tid,starttime,endtime);
    }
}
