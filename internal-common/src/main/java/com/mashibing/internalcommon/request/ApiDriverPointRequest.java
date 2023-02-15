package com.mashibing.internalcommon.request;

import lombok.Data;

@Data
public class ApiDriverPointRequest {
    public Long carId;

    public PointDTO[] points;
}
