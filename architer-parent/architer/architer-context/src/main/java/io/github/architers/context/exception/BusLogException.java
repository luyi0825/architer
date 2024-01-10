package io.github.architers.context.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * 业务异常（用户自己在业务中抛出的异常）-错误码默认400
 *
 * @author luyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusLogException extends BaseCodeException {
    public static final int BUS_EXCEPTION_CODE = HttpStatus.BAD_REQUEST.value();

    public BusLogException(String message) {
        super(message);
        this.code = BUS_EXCEPTION_CODE;
    }

    public BusLogException(int code, String message) {
        super(code, message);
    }

    public BusLogException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusLogException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
