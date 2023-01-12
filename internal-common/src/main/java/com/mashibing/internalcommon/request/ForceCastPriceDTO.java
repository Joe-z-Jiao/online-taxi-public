package com.mashibing.internalcommon.request;

import lombok.Data;

@Data
public class ForceCastPriceDTO {
    private String depLongitude;
    private String depLatitude;
    private String destLongitude;
    private String destLatitude;
}
