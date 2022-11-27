package io.github.architers.web.flux;


import io.github.architers.context.exception.DefaultExceptionHandler;
import io.github.architers.context.exception.GlobalExceptionHandler;
import io.github.architers.context.exception.RequestExceptionHandler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * springMVC自动配置类
 *
 * @author luyi
 */
@Configuration(proxyBeanMethods = false)
public class WebFluxAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RequestExceptionHandler requestExceptionHandler() {
        return new DefaultExceptionHandler();
    }


    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler(RequestExceptionHandler requestExceptionHandler) {
        return new GlobalExceptionHandler(requestExceptionHandler);
    }



}
