package com.architecture.context.common;

/**
 * 定义service常用异常
 *
 * @author luyi
 * @date 2020/12/19 下午11:55
 */
public enum ResponseStatusEnum {
    /**
     * 构造状态码信息
     */
    SYSTEM_EXCEPTION(500, "亲,服务器开小差啦,请稍后再试"),
    SERVICE_EXCEPTION(400, "操作失败"),
    SUCCESS(200, "操作成功"),
    NO_LOGIN(401, "请登录"),
    /**
     * 没有权限
     */
    NO_PERMISSION(403, "没有权限"),
    /**
     * 参数校验抛出的异常
     */
    PARAMS_VALID_EXCEPTION(100, "参数校验异常");

    ResponseStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 状态码
     */
    private final int code;
    /**
     * 错误消息
     */
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
