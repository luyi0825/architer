package io.github.architers.context.exception;

import lombok.Data;

/**
 * 不记录栈信息的异常，主要给用户看，不记录日志信息
 *
 * @author luyi
 */
@Data
public class NoLogStackException extends RuntimeException {

    /**
     * 异常编码
     */
    private int code = 400;

    /**
     * 错误信息
     */
    private String message;

    public NoLogStackException(String message) {
        super(message);
        this.message = message;
    }

    public NoLogStackException(String message, int code) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
