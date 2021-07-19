package com.architecture.ultimate.starter.web;


import com.architecture.ultimate.starter.web.exception.GlobalExceptionHandler;
import com.architecture.ultimate.starter.web.response.BaseResponseBodyAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 */
@Configuration
public class StarterWebConfig {

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    public BaseResponseBodyAdvice baseResponseBodyAdvice() {
        return new BaseResponseBodyAdvice();
    }
}
