package io.github.architers.context.web;

import io.github.architers.context.exception.BusErrorException;
import io.github.architers.context.exception.BusException;
import io.github.architers.context.exception.SysException;
import lombok.extern.slf4j.Slf4j;

/**
 * web异常处理器
 *
 * @author luyi
 * @since 1.0.2
 */
@Slf4j
public final class ExceptionHandlerUtils {

    public static ResponseResult<?> handler(Throwable e, RequestExceptionHandler requestExceptionHandler) {
        if (e instanceof BusErrorException) {
            BusErrorException busLogException = (BusErrorException) e;
            log.error(e.getMessage(), e);
            return new ResponseResult<>(busLogException.getCode(), e.getMessage());
        } else if (e instanceof BusException) {
            BusException busLogException = (BusException) e;
            log.warn(e.getMessage());
            return new ResponseResult<>(busLogException.getCode(), e.getMessage());
        }
        if (requestExceptionHandler != null) {
            ResponseResult<?> responseResult;  responseResult = requestExceptionHandler.handler(e);
            if(responseResult!=null){
                return responseResult;
            }
        }
        //不可预期的异常
        log.error(e.getMessage(), e);
        return new ResponseResult<>(SysException.SYS_EXCEPTION_CODE, SysException.SYS_EXCEPTION_MESSAGE, e.getMessage());
    }
}
