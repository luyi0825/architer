
package io.github.architers.cache.redisson;


import io.github.architers.cache.redisson.support.RedissonMapCacheOperate;
import io.github.architers.cache.redisson.support.RedissonValueCacheOperate;
import io.github.architers.propertconfig.redisson.RedissonBeanName;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * redisson缓存自动配置类
 *
 * @author luyi
 */
@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonCacheAutoConfiguration {

    @Resource
    private RedissonProperties redissonProperties;

    @Resource
    private ApplicationContext ctx;

    @Bean(destroyMethod = "shutdown", name = RedissonBeanName.CACHE_BEAN_NAME)
    @ConditionalOnMissingBean(name = {RedissonBeanName.CACHE_BEAN_NAME})
    public RedissonClient redissonCacheClient() {
        if (redissonProperties == null) {
            throw new IllegalArgumentException("redisson缓存配置缺失");
        }
        return redissonProperties.createClient();
    }


//    @Bean
//    public RedissonConnectionFactory redissonConnectionFactory(@Qualifier(CACHE_CLIENT_BEAN_NAME) RedissonClient redisson) {
//        return new RedissonConnectionFactory(redisson);
//    }
//
//    @Bean("cacheRedisTemplate")
//    public org.springframework.data.redis.core.RedisTemplate<Object, Object> redisTemplate(RedissonConnectionFactory redisConnectionFactory) {
//        RedisSerializer<Object> serializer = getRedisSerializer();
//        org.springframework.data.redis.core.RedisTemplate<Object, Object> redisTemplate = new org.springframework.data.redis.core.RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(serializer);
//        redisTemplate.setHashKeySerializer(serializer);
//        redisTemplate.setHashValueSerializer(serializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }

    @Bean
    public RedissonValueCacheOperate valueCacheOperate() {
        return new RedissonValueCacheOperate(redissonCacheClient());
    }

    @Bean
    public RedissonMapCacheOperate mapCacheOperate() {
        return new RedissonMapCacheOperate(redissonCacheClient());
    }

//    @Bean("cacheStringRedisTemplate")
//    public StringRedisTemplate stringRedisTemplate(RedissonConnectionFactory redisConnectionFactory) {
//        RedisSerializer<Object> serializer = getRedisSerializer();
//        StringRedisTemplate redisTemplate = new StringRedisTemplate();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        redisTemplate.setKeySerializer(serializer);
//        redisTemplate.setValueSerializer(serializer);
//        redisTemplate.setHashKeySerializer(serializer);
//        redisTemplate.setHashValueSerializer(serializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//
//    /**
//     * 得到redis的序列化器
//     */
//    private RedisSerializer<Object> getRedisSerializer() {
//        return new GenericJackson2JsonRedisSerializer();
//    }

//    @Bean
//    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//    public RedisTemplateCacheService redisValueService(org.springframework.data.redis.core.RedisTemplate<Object, Object> redisTemplate) {
//        return new RedisTemplateCacheService(redisTemplate);
//    }


//    @Bean
//    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//    public RedisCacheOperate redisCacheManager(RedisTemplateCacheService redisTemplateCacheService,
//                                               @Qualifier(CACHE_CLIENT_BEAN_NAME) RedissonClient redissonClient) {
//        RedisCacheOperate redisCacheManager = new RedisCacheOperate();
//        redisCacheManager.setRedisTemplateService(redisTemplateCacheService);
//        redisCacheManager.setRedissonClient(redissonClient);
//        return redisCacheManager;
//    }

}
