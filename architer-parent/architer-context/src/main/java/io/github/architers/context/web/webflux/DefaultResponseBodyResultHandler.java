package io.github.architers.context.web.webflux;

import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.AbstractMessageWriterResultHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

public class DefaultResponseBodyResultHandler extends AbstractMessageWriterResultHandler {

    protected DefaultResponseBodyResultHandler(List<HttpMessageWriter<?>> messageWriters, RequestedContentTypeResolver contentTypeResolver) {
        super(messageWriters, contentTypeResolver);
    }

    protected DefaultResponseBodyResultHandler(List<HttpMessageWriter<?>> messageWriters, RequestedContentTypeResolver contentTypeResolver, ReactiveAdapterRegistry adapterRegistry) {
        super(messageWriters, contentTypeResolver, adapterRegistry);
    }

    @Override
    protected Mono<Void> writeBody(Object body, MethodParameter bodyParameter, ServerWebExchange exchange) {
        return super.writeBody(body, bodyParameter, exchange);
    }

    @Override
    protected Mono<Void> writeBody(Object body, MethodParameter bodyParameter, MethodParameter actualParam, ServerWebExchange exchange) {
        return super.writeBody(body, bodyParameter, actualParam, exchange);
    }
}
