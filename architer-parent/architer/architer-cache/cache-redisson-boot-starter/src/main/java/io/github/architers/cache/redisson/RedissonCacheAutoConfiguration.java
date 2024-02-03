
package io.github.architers.cache.redisson;


import io.github.architers.cache.redisson.support.RedissonMapCacheOperate;
import io.github.architers.cache.redisson.support.RedissonValueCacheOperate;
import io.github.architers.propertconfig.redisson.RedissonBeanName;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * redisson缓存自动配置类
 *
 * @author luyi
 * @since 1.0.2
 */
@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
@ConditionalOnProperty(prefix = RedissonProperties.prefix, name = "enabled", havingValue = "true", matchIfMissing = true)
public class RedissonCacheAutoConfiguration {

    @Resource
    private RedissonProperties redissonProperties;

    @Bean(destroyMethod = "shutdown", name = RedissonBeanName.CACHE_BEAN_NAME)
    @ConditionalOnMissingBean(name = {RedissonBeanName.CACHE_BEAN_NAME})
    public RedissonClient redissonCacheClient() {
        if (redissonProperties == null) {
            throw new IllegalArgumentException("redisson缓存配置缺失");
        }
        return redissonProperties.createClient();
    }


    @Bean
    public RedissonValueCacheOperate valueCacheOperate() {
        return new RedissonValueCacheOperate(redissonCacheClient());
    }

    @Bean
    public RedissonMapCacheOperate mapCacheOperate() {
        return new RedissonMapCacheOperate(redissonCacheClient());
    }


}
