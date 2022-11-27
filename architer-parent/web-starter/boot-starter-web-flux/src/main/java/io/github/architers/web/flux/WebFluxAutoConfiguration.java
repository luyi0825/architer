package io.github.architers.web.flux;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.architers.context.exception.DefaultExceptionHandler;
import io.github.architers.context.exception.GlobalExceptionHandler;
import io.github.architers.context.exception.RequestExceptionHandler;
import io.github.architers.web.common.response.ResponseResultBodyAdvice;
import io.github.architers.web.common.response.ResponseResultHttpMessageConverter;
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


}
