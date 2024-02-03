package io.github.architers.context.cache;

import io.github.architers.common.expression.method.ExpressionParser;
import io.github.architers.context.cache.consistency.rocketmq.CacheRocketAutoConfiguration;
import io.github.architers.context.cache.fieldconvert.CacheFieldConvertProperties;
import io.github.architers.context.cache.fieldconvert.FieldConvertSupport;
import io.github.architers.context.cache.fieldconvert.utils.FieldConvertUtils;
import io.github.architers.context.cache.operate.CacheOperateConfiguration;
import io.github.architers.context.cache.proxy.CacheAnnotationsParser;
import io.github.architers.context.cache.proxy.CacheProxySupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Role;

/**
 * 缓存自动配置类
 *
 * @author luyi
 */
@Configuration(proxyBeanMethods = true)
@EnableConfigurationProperties({CacheProperties.class, CacheFieldConvertProperties.class})
@Import({CacheOperateConfiguration.class, CacheRocketAutoConfiguration.class})

public class CacheAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheAnnotationsParser cacheAnnotationsParser() {
        return new CacheAnnotationsParser();
    }


    @Bean
    public CacheProxySupport cacheProxySupport() {
        return new CacheProxySupport();
    }

    @Bean
    public FieldConvertSupport fieldConvertSupport() {
        return new FieldConvertSupport();
    }

    @Bean
    public FieldConvertUtils fieldConvertUtils() {
        return new FieldConvertUtils();
    }

    @Bean
    public ExpressionParser expressionParser() {
        return new ExpressionParser();
    }

    @Bean
    public CacheNameManager cacheNameManager() {
        return new CacheNameManager();
    }


}
