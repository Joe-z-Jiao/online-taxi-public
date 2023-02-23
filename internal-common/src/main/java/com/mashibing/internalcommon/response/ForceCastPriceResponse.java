package com.mashibing.internalcommon.response;

import lombok.Data;

@Data
public class ForceCastPriceResponse {
    private Double price;
    private String cityCode;
    private String vehicleType;
    private String fareType;
    private Integer fareVersion;
}
