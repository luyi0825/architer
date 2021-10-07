package com.bussiness.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class CrossConfig {
    //@Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //1、配置跨域
        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("http://localhost:8001/");
//        corsConfiguration.addAllowedOrigin("http://localhost:8888/");
//        corsConfiguration.addAllowedOrigin("http://localhost:8002");
//        corsConfiguration.addAllowedOrigin("http://localhost:8003/");
//        corsConfiguration.add

        //允许任何头
        corsConfiguration.addAllowedHeader("*");
        //允许任何方法（post、get等）
        corsConfiguration.addAllowedMethod("*");
        //允许任何域名使用
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addExposedHeader("token");
        //HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600"
        //cookie访问
        corsConfiguration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(source);
    }


    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            String origin = request.getHeaders().getOrigin();
            if (origin == null || "".equals(origin)) {
                return chain.filter(ctx);
            }
            HttpHeaders requestHeaders = request.getHeaders();
            ServerHttpResponse response = ctx.getResponse();
            HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
            HttpHeaders headers = response.getHeaders();
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
            headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders.getAccessControlRequestHeaders());
            if (requestMethod != null) {
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
            }
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
            headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
            if (request.getMethod() == HttpMethod.OPTIONS) {
                response.setStatusCode(HttpStatus.OK);
                return Mono.empty();
            }
            return chain.filter(ctx);
        };
    }


//    @Bean
//    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
//        return new HiddenHttpMethodFilter() {
//            @Override
//            public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//                return chain.filter(exchange);
//            }
//        };
//    }
}
