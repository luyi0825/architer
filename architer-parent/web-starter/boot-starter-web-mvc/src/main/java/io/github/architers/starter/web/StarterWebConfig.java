package io.github.architers.starter.web;


import io.github.architers.starter.web.exception.DefaultExceptionHandler;
import io.github.architers.starter.web.exception.GlobalExceptionHandler;
import io.github.architers.starter.web.response.ResponseResultBodyAdvice;
import io.github.architers.starter.web.response.ResponseResultHttpMessageConverter;
import io.github.architers.starter.web.response.WebmvcConfiguration;
import io.github.architers.context.web.RequestExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 */
@Configuration(proxyBeanMethods = false)
public class StarterWebConfig {

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

    @Bean
    @ConditionalOnMissingBean
    public ResponseResultBodyAdvice baseResponseBodyAdvice() {
        return new ResponseResultBodyAdvice();
    }

    /**
     * 注入自定义消息转换器
     * ObjectMapper已经在springMvc的代码中注入
     */
    @Bean
    public ResponseResultHttpMessageConverter responseResultHttpMessageConverter(ObjectMapper objectMapper) {
        return new ResponseResultHttpMessageConverter(objectMapper);
    }

    @Bean
    public WebmvcConfiguration webmvcConfiguration() {
        return new WebmvcConfiguration();
    }
}
