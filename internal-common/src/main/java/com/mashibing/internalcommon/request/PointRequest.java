package com.mashibing.internalcommon.request;

import lombok.Data;

@Data
public class PointRequest {
    private String trid;

    private String tid;

    private PointDTO[] points;
}
