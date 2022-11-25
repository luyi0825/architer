package io.github.architers.context.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 系统异常-错误码500
 * <li>处理不可预期的异常</li>
 *
 * @author luyi
 */
@Data
public class SysException extends BaseCodeException {

    /**
     * 系统异常对应的编码
     */
    public static final int SYS_EXCEPTION_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();
    public static final String SYS_EXCEPTION_MESSAGE = "系统异常";

    /**
     * 常量异常
     */
    public static final SysException EXCEPTION = new SysException();

    public SysException() {
        this.code = SYS_EXCEPTION_CODE;
        this.message = SYS_EXCEPTION_MESSAGE;
    }

    public SysException(String message) {
        super(message);
        this.message = message;
    }

    public SysException(Throwable cause) {
        super(SYS_EXCEPTION_MESSAGE, cause);
        this.code = SYS_EXCEPTION_CODE;
    }

    public SysException(String message, Throwable cause) {
        super(message, cause);
        this.code = SYS_EXCEPTION_CODE;
    }
}
