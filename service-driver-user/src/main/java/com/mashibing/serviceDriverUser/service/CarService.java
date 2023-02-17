package com.mashibing.serviceDriverUser.service;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrackResponse;
import com.mashibing.serviceDriverUser.mapper.CarMapper;
import com.mashibing.serviceDriverUser.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarService {

    @Autowired
    CarMapper carMapper;

    @Autowired
    ServiceMapClient serviceMapClient;

    public ResponseResult addCar(Car car) {
        LocalDateTime now = LocalDateTime.now();
        car.setGmtCreate(now);
        car.setGmtModified(now);
//保存车辆
        carMapper.insert(car);
        //获得此车辆对应的 tid
        ResponseResult<TerminalResponse> result = serviceMapClient.addTerminal(car.getVehicleNo(), car.getId() + "");
        String tid = result.getData().getTid();
        car.setTid(tid);

        //获得车辆的 trid
        ResponseResult<TrackResponse> trackResponseResponseResult = serviceMapClient.addTrack(tid);
        TrackResponse data = trackResponseResponseResult.getData();
        String trackId = data.getTrackId();
        String trackName = data.getTrackName();
        car.setTrid(trackId);
        car.setTrname(trackName);

        carMapper.updateById(car);
        return ResponseResult.success("");
    }

    public ResponseResult<Car> getCar(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        List<Car> cars = carMapper.selectByMap(map);
        return ResponseResult.success(cars.get(0));
    }
}
