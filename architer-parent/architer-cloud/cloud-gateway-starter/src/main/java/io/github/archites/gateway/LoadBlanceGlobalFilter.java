package io.github.archites.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class LoadBlanceGlobalFilter implements GlobalFilter {

    @Value("${spring.cloud.archites.load-balance.relase-version}")
    private String relaseVersion;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getRequest().getHeaders().add("relase.version", relaseVersion);
        return chain.filter(exchange);
    }
}
