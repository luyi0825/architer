package io.github.architers.context.web.flux;

import io.github.architers.context.web.ResponseResult;
import io.github.architers.context.web.ExceptionHandlerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * webFlux全局异常处理
 *
 * @author luyi
 * @since 1.0.2
 */
@Slf4j
public class WebFluxGlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Autowired(required = false)
    private WebFluxExceptionHandler webFluxExceptionHandler;


    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);
        ResponseResult<?> responseResult = ExceptionHandlerUtils.handler(ex, webFluxExceptionHandler);
        return MonoUtils.writeWith(response, responseResult);
    }
}
