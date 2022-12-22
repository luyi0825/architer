package io.github.architers.context.exception;


/**
 * 不记录栈信息的业务异常，主要给用户看，不记录日志信息
 *
 * @author luyi
 */
public class NoStackBusException extends BusException {


    public NoStackBusException(String message) {
        super(message);
    }

    public NoStackBusException(int code, String message) {
        super(code, message);
    }
}
