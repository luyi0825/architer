package io.github.architers.context.web;


/**
 * 请求异处理器
 *
 * @author luyi
 * @since 1.0.0
 */
public interface RequestExceptionHandler {

    /**
     * 处理异常
     *
     * @param e 处理的异常
     * @return 响应结果
     */
    ResponseResult<?> handler(Throwable e);

}
