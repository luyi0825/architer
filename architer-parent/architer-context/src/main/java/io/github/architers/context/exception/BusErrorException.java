package io.github.architers.context.exception;

import org.springframework.http.HttpStatus;

/**
 * 业务异常（用户自己在业务中抛出的异常）-错误码默认400
 *
 * @author luyi
 */
public class BusErrorException extends BusException {
    public static final int BUS_EXCEPTION_CODE = HttpStatus.BAD_REQUEST.value();

    public BusErrorException(String message) {
        super(message);
        this.code = BUS_EXCEPTION_CODE;
    }

    public BusErrorException(int code, String message) {
        super(code, message);
    }

    public BusErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusErrorException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
