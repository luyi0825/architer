package com.architecture.ultimate.module.common.response;


import com.architecture.ultimate.module.common.StatusCode;

import java.io.Serializable;

/**
 * @author ly
 * 请求结果
 */
public class ResponseResult implements Serializable {


    /**
     * 返回码
     */
    private Integer code;
    /**
     * 消息
     */
    private String message;
    /**
     * 返回数据
     */
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseResult() {

    }

    public ResponseResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 成功
     */
    public static ResponseResult ok() {
        ResponseResult baseResponse = new ResponseResult();
        baseResponse.setCode(StatusCode.SUCCESS.getCode());
        baseResponse.setMessage(StatusCode.SUCCESS.getMessage());
        return baseResponse;
    }

    /**
     * 成功
     */
    public static ResponseResult ok(Object data) {
        ResponseResult baseResponse = new ResponseResult();
        baseResponse.setCode(StatusCode.SUCCESS.getCode());
        baseResponse.setMessage(StatusCode.SUCCESS.getMessage());
        baseResponse.setData(data);
        return baseResponse;
    }

    /**
     * 失败
     */
    public static ResponseResult fail() {
        ResponseResult baseResponse = new ResponseResult();
        baseResponse.setCode(StatusCode.SERVICE_EXCEPTION.getCode());
        baseResponse.setMessage(StatusCode.SERVICE_EXCEPTION.getMessage());
        return baseResponse;
    }

    /**
     * 失败
     */
    public static ResponseResult fail(Object data) {
        ResponseResult baseResponse = new ResponseResult();
        baseResponse.setCode(StatusCode.SERVICE_EXCEPTION.getCode());
        baseResponse.setData(data);
        baseResponse.setMessage(StatusCode.SERVICE_EXCEPTION.getMessage());
        return baseResponse;
    }


}
