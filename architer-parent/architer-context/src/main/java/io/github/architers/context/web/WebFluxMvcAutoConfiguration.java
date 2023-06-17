package io.github.architers.context.web;

import io.github.architers.context.web.flux.WebFluxGlobalExceptionHandler;
import io.github.architers.context.web.flux.WebFluxExceptionHandler;
import io.github.architers.context.web.flux.WebFluxResponseResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;

/**
 * webmvc自配配置类(WebFlux)
 *
 * @author luyi
 * @since 1.0.2
 */
@Configuration(proxyBeanMethods = false)
public class WebFluxMvcAutoConfiguration {



    @Bean
    public WebFluxGlobalExceptionHandler webFluxGlobalWebExceptionHandler(@Autowired(required = false) WebFluxExceptionHandler webFluxExceptionHandler) {
        WebFluxGlobalExceptionHandler webExceptionHandler = new WebFluxGlobalExceptionHandler();
        return webExceptionHandler;
    }

    @Bean
    public WebFluxResponseResultHandler defaultResponseBodyResultHandler(@Qualifier("webFluxAdapterRegistry") ReactiveAdapterRegistry reactiveAdapterRegistry,
                                                                         ServerCodecConfigurer serverCodecConfigurer,
                                                                         @Qualifier("webFluxContentTypeResolver") RequestedContentTypeResolver contentTypeResolver) {
        return new WebFluxResponseResultHandler(serverCodecConfigurer.getWriters(),contentTypeResolver,reactiveAdapterRegistry);
    }




}
