package io.github.architers.context.web;

import io.github.architers.context.web.servlet.ServletExceptionHandler;
import io.github.architers.context.web.servlet.ServletResponseResultHandler;
import io.github.architers.context.web.servlet.ServletGlobalWebExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * webmvc自配配置类
 *
 * @author luyi
 * @since 1.0.2
 */
@Configuration(proxyBeanMethods = false)
public class WebServletMvcAutoConfiguration {


    /**
     * 异常处理器
     */
    @Bean
    @ConditionalOnMissingBean
    public ServletExceptionHandler requestExceptionHandler() {
        return new ServletExceptionHandler();
    }

    /**
     * Servlet全局web异常处理器
     */
    @Bean
    public ServletGlobalWebExceptionHandler servletGlobalWebExceptionHandler() {
        return new ServletGlobalWebExceptionHandler();
    }

    /**
     * ResponseResul结果处理
     */
    @Bean
    public ServletResponseResultHandler responseResultBodyAdvice() {
        return new ServletResponseResultHandler();
    }


}
