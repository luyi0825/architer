package com.core.module.common.exception;

/**
 * 参数校验异常
 *
 * @author luyi
 */
public class ParamsValidException extends RuntimeException {

    public ParamsValidException() {
        super();
    }

    public ParamsValidException(String message) {
        super(message);
    }

    public ParamsValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamsValidException(Throwable cause) {
        super(cause);
    }

    protected ParamsValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
