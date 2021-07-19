package com.core.module.common.exception;

/**
 * 业务异常
 *
 * @author luyi
 */
public class ServiceException extends RuntimeException {
    /**
     * 异常编码，帮助异常快速定位
     */
    private int code = 1;

    public ServiceException() {

    }

    public ServiceException(String message) {

    }

    public ServiceException(int code) {
        this.code = code;
    }

    public ServiceException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public ServiceException(Throwable cause, int code) {
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
