package com.core.excel.html.exception;

/**
 * @author luyi
 * html类型异常
 */
public class HtmlTypeException extends RuntimeException {

    public HtmlTypeException() {
    }

    public HtmlTypeException(String message) {
        super(message);
    }

    public HtmlTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public HtmlTypeException(Throwable cause) {
        super(cause);
    }

    public HtmlTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
