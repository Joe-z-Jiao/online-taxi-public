package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.remote.TerminaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class TerminalService {
    @Autowired
    private TerminaClient terminaClient;

    public ResponseResult add(String name) {
        return terminaClient.add(name);
    }
}
