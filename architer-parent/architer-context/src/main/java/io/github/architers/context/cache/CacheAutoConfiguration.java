package io.github.architers.context.cache;

import io.github.architers.context.cache.operate.CacheOperateConfiguration;
import io.github.architers.context.cache.operate.CacheOperateSupport;
import io.github.architers.context.cache.proxy.CacheAnnotationsParser;
import io.github.architers.context.cache.proxy.CacheProxySupport;
import io.github.architers.context.expression.ExpressionParser;
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
@EnableConfigurationProperties(CacheProperties.class)
@Import(CacheOperateConfiguration.class)
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
    public ExpressionParser expressionParser() {
        return new ExpressionParser();
    }
}
