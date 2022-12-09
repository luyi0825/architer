package io.github.architers.context.exception;


/**
 * 业务异常
 *
 * @author luyi
 */
public abstract class BaseCodeException extends RuntimeException {
    /**
     * 异常编码，帮助异常快速定位
     */
    protected int code = 500;

    protected String message = "系统异常";

    public BaseCodeException() {

    }

    public BaseCodeException(String message) {
        super(message);
        this.message = message;
    }

    public BaseCodeException(int code) {
        this.code = code;
    }

    public BaseCodeException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseCodeException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BaseCodeException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
