package io.github.architers.context.web.flux;

import io.github.architers.context.web.RequestExceptionHandler;
import io.github.architers.context.web.ResponseResult;
import lombok.extern.slf4j.Slf4j;

/**
 * WebFlux的异常处理器
 *
 * @author luyi
 */
@Slf4j
public class WebFluxExceptionHandler implements RequestExceptionHandler {


    @Override
    public ResponseResult<?> handler(Throwable e) {
        return null;
    }


}
