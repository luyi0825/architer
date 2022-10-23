package io.github.architers.gateway;

import io.github.architers.context.exception.RequestExceptionHandler;
import io.github.architers.context.web.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;


/**
 * @author luyi
 */
@Component
@Slf4j
public class GloadErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    @Resource
    private RequestExceptionHandler requestExceptionHandler;


    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);
        ResponseResult<?> responseResult;
        if (ex instanceof NotFoundException) {
            responseResult = new ResponseResult<>(HttpStatus.BAD_GATEWAY.value(), "服务未找到");
        } else {
            responseResult = requestExceptionHandler.handler(ex);
        }
        return MonoUtils.writeWith(response, responseResult);

    }
}
