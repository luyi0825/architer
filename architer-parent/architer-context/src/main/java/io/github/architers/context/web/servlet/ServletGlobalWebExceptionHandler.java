package io.github.architers.context.web.servlet;


import io.github.architers.context.web.RequestExceptionHandler;
import io.github.architers.context.web.ResponseResult;
import io.github.architers.context.web.ExceptionHandlerUtils;
import lombok.extern.slf4j.Slf4j;
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
 * @since 2020/12/9
 */
@RestControllerAdvice
@Slf4j
public class ServletGlobalWebExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ServletGlobalWebExceptionHandler.class);

    private RequestExceptionHandler requestExceptionHandler;

    public ServletGlobalWebExceptionHandler() {
        logger.info("初始化全局异常处理");
    }

    /**
     * 处理系统异常
     */
    @ExceptionHandler(value = Throwable.class)
    public ResponseResult<?> exceptionHandler(Throwable e) {
        return ExceptionHandlerUtils.handler(e, requestExceptionHandler);
    }

    public void setRequestExceptionHandler(RequestExceptionHandler requestExceptionHandler) {
        this.requestExceptionHandler = requestExceptionHandler;
    }
}
