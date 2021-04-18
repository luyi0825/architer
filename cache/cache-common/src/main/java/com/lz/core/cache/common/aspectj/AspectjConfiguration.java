package com.lz.core.cache.common.aspectj;

import com.lz.core.cache.common.CacheProcess;
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
    @Autowired(required = false)
    public CacheAspectj cacheAspectj(CacheProcess cacheProcess) {
        return new CacheAspectj(cacheProcess);
    }
}
