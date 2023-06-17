package io.github.architers.context.web;

/**
 * 定义service常用异常
 *
 * @author luyi
 * @date 2020/12/19 下午11:55
 */
public enum ResponseStatusEnum {


    /**
     * 系统错误
     */
    SYS_ERROR(500, ""),
    /**
     * 业务异常
     */
    BusException(400, "参数校验失败"),
    SUCCESS(200, "操作成功"),
    NO_LOGIN(401, "请登录"),
    /**
     * 没有权限
     */
    NO_PERMISSION(403, "没有权限");


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
