package com.mashibing.servicemap.service;


import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.remote.ServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceFromMapService {

    @Autowired
    ServiceClient serviceClient;

    public ResponseResult add(String name){
        return serviceClient.add(name);
    }
}
