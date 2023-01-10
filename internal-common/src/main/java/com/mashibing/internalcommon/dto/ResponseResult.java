package com.mashibing.internalcommon.dto;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessorType;

@Data
@Accessors(chain = true)
public class ResponseResult<T> {
    private int code;
    private String message;
    private T data;

    /**
     * 成功相应的方法
     * @return
     * @param <T>
     */
    public static <T> ResponseResult success(){
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getValue());
    }

    /**
     * 成功响应
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ResponseResult success(T data) {
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getValue()).setData(data);
    }

    /**
     * 自定义失败，只返回
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ResponseResult fail(T data) {
        return new ResponseResult().setData(data);
    }

    /**
     * 自定义失败,提示码，提示信息
     * @param code
     * @param message
     * @return
     */
    public static ResponseResult fail(int code,String message) {
        return new ResponseResult().setCode(code).setMessage(message);
    }

    /**
     * 自定义失败，提示码，提示信息，具体错误信息
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static ResponseResult fail(int code,String message,String data) {
        return new ResponseResult().setCode(code).setMessage(message).setData(data);
    }

}
