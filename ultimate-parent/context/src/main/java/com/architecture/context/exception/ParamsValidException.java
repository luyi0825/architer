package com.architecture.context.exception;

/**
 * 参数校验异常
 *
 * @author luyi
 */
public class ParamsValidException extends RuntimeException {

    /**
     * 异常编码，帮助异常快速定位
     */
    private int code = 2;

    public ParamsValidException() {

    }

    public ParamsValidException(int code) {
        this.code = code;
    }

    public ParamsValidException(String message) {
        super(message);
    }

    public ParamsValidException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ParamsValidException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public ParamsValidException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
