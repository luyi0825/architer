package io.github.architers.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoadBlanceGlobalFilter implements GlobalFilter {

    //@Value("${spring.cloud.architer.load-balance.release-version}")
    private String relaseVersion;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        System.out.println(path);
        //exchange.getRequest().getHeaders().add("relase.version", relaseVersion);
        return chain.filter(exchange);
    }
}
