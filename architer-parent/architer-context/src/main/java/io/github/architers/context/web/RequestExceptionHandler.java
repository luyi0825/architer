package io.github.architers.context.web;

/**
 * 请求异常数据
 *
 * @author luyi
 */
public interface RequestExceptionHandler {

    /**
     * 处理异常
     *
     * @param e 处理的异常
     * @return 响应结果
     */
    ResponseResult<?> handler(Exception e);

}
