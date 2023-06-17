package io.github.architers.context.web.flux;

import io.github.architers.context.web.IgnoreResponseResult;
import io.github.architers.context.web.ResponseResult;
import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * webFlux对应的responseResult处理：
 * 统一返回ResponseResult实体结果，如果不想返回该实体结果就再方法上添加IgnoreResponseResult注解
 *
 * @author luyi
 * @since 1.0.2
 */
public class WebFluxResponseResultHandler extends ResponseBodyResultHandler {

    public WebFluxResponseResultHandler(List<HttpMessageWriter<?>> messageWriters, RequestedContentTypeResolver contentTypeResolver) {
        super(messageWriters, contentTypeResolver);
        super.setOrder(-100);
    }

    public WebFluxResponseResultHandler(List<HttpMessageWriter<?>> messageWriters, RequestedContentTypeResolver contentTypeResolver, ReactiveAdapterRegistry adapterRegistry) {
        super(messageWriters, contentTypeResolver, adapterRegistry);
        super.setOrder(-100);

    }

    @Override
    public boolean supports(HandlerResult result) {
        MethodParameter returnType = result.getReturnTypeSource();
        Class<?> containingClass = returnType.getContainingClass();
        if (returnType.hasMethodAnnotation(IgnoreResponseResult.class)) {
            return false;
        }
        return (AnnotatedElementUtils.hasAnnotation(containingClass, RestController.class) ||
                returnType.hasMethodAnnotation(RestController.class));
    }

    @Override
    protected Mono<Void> writeBody(Object body, MethodParameter bodyParameter, ServerWebExchange exchange) {
        ResponseResult<?> responseResult;
        if (body instanceof ResponseResult) {
            responseResult = (ResponseResult<?>) body;
        } else {
            responseResult = ResponseResult.ok(body);
        }
        return MonoUtils.writeWith(exchange.getResponse(), responseResult);
    }

    @Override
    protected Mono<Void> writeBody(Object body, MethodParameter bodyParameter, MethodParameter actualParam, ServerWebExchange exchange) {
        ResponseResult<?> responseResult;
        if (body instanceof ResponseResult) {
            responseResult = (ResponseResult<?>) body;
        } else {
            responseResult = ResponseResult.ok(body);
        }
        return MonoUtils.writeWith(exchange.getResponse(), responseResult);
    }
}
