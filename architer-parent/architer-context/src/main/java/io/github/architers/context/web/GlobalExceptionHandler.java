package io.github.architers.context.web;


import io.github.architers.context.exception.NoLogStackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * <p>@RestControllerAdvice=@ControllerAdvice+@ResponseBody</p>
 *
 * @author luyi
 * @date 2020/12/9
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final RequestExceptionHandler requestExceptionHandler;

    public GlobalExceptionHandler(RequestExceptionHandler requestExceptionHandler) {
        logger.info("初始化全局异常处理");
        this.requestExceptionHandler = requestExceptionHandler;
    }


    @ExceptionHandler(value = NoLogStackException.class)
    public ResponseResult<?> exceptionHandler(NoLogStackException e) {
        logger.info(e.getMessage());
        return new ResponseResult<>(e.getCode(), e.getMessage());
    }

    /**
     * @author luyi
     * 处理系统异常
     */
    @ExceptionHandler(value = Throwable.class)
    public ResponseResult<?> exceptionHandler(Exception e) {
        return requestExceptionHandler.handler(e);


    }




}
