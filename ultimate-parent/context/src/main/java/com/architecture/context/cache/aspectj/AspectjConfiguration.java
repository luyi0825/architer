package com.architecture.context.cache.aspectj;

import com.architecture.context.cache.CacheAnnotationsParser;
import com.architecture.context.cache.CacheProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 * Aspectj 缓存配置的类
 */
@Configuration
public class AspectjConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CacheAnnotationsParser cacheAnnotationsParser() {
        return new CacheAnnotationsParser();
    }


    @Bean
    @ConditionalOnMissingBean
    @Autowired(required = false)
    public com.architecture.context.cache.aspectj.CacheAspectj cacheAspectj(CacheProcess cacheProcess, CacheAnnotationsParser cacheAnnotationsParser) {
        return new com.architecture.context.cache.aspectj.CacheAspectj(cacheProcess, cacheAnnotationsParser);
    }
}
