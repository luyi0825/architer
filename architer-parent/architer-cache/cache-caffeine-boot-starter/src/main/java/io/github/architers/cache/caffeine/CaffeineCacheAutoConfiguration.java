package io.github.architers.cache.caffeine;

import io.github.architers.cache.caffeine.support.CaffeineMapCacheOperate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CaffeineProperties.class)
public class CaffeineCacheAutoConfiguration {

    @Bean
    public ExpireAfter expireAfter(CaffeineProperties caffeineProperties){
        return new ExpireAfter();
    }


    @Bean
    public CaffeineCacheFactory caffeineCacheFactory(ExpireAfter expireAfter,CaffeineProperties caffeineProperties) {
        return new CaffeineCacheFactory(expireAfter,caffeineProperties);
    }

    @Bean
    public CaffeineMapCacheOperate caffeineMapCacheOperate(CaffeineCacheFactory caffeineCacheFactory) {
        return new CaffeineMapCacheOperate(caffeineCacheFactory);
    }


}
