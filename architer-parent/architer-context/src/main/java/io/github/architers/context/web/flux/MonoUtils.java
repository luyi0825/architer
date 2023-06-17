package io.github.architers.context.web.flux;

import io.github.architers.context.utils.JsonUtils;
import io.github.architers.context.web.ResponseResult;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

public class MonoUtils {

    public static Mono<Void> writeWith(ServerHttpResponse response, ResponseResult<?> responseResult ) {
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            return bufferFactory.wrap(JsonUtils.toJsonBytes(responseResult));
        }));
    }
}
