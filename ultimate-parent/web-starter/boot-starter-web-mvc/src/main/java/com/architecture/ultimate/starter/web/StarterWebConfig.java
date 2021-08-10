package com.architecture.ultimate.starter.web;


import com.architecture.ultimate.starter.web.exception.GlobalExceptionHandler;
import com.architecture.ultimate.starter.web.response.ResponseResultBodyAdvice;
import com.architecture.ultimate.starter.web.response.ResponseResultHttpMessageConverter;
import com.architecture.ultimate.starter.web.response.WebmvcConfiguration;
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
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
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
