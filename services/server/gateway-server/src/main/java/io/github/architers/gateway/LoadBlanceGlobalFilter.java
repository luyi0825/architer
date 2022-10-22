package io.github.architers.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoadBlanceGlobalFilter implements GlobalFilter {

    //@Value("${spring.cloud.architer.load-balance.release-version}")
    private String relaseVersion;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        log.info("请求接口:{}", path);
        if (path.startsWith("/public/")) {
            return chain.filter(exchange);
        }
        return chain.filter(exchange);
        //exchange.getRequest().getHeaders().add("relase.version", relaseVersion);

    }
}
