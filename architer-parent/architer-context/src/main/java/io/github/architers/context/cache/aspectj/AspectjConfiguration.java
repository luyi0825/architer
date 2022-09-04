package io.github.architers.context.cache.aspectj;

import io.github.architers.context.cache.CacheAnnotationsParser;
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
    public CacheAspectj cacheAspectj(CacheAnnotationsParser cacheAnnotationsParser) {
        return new CacheAspectj(cacheAnnotationsParser);
    }
}
