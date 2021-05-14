package com.lz.core.service;

/**
 * 定义service常用异常
 *
 * @author luyi
 * @date 2020/12/19 下午11:55
 */
public enum StatusCode {
    /**
     * 构造状态码信息
     */
    SYSTEM_EXCEPTION(500, "亲,服务器开小差啦,请稍后再试"),
    BUS_EXCEPTION(400, "操作失败"),
    SUCCESS(200, "操作成功"),
    NO_LOGIN(401, "请登录"),
    NO_PERMISSION(403, "没有权限"),

    PARAMS_VALID_EXCEPTION(100, "IP地址发生改变");

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 状态码
     */
    private int code;
    /**
     * 错误消息
     */
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
