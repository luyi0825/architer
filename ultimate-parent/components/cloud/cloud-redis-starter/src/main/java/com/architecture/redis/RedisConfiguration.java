
package com.architecture.redis;


import com.architecture.context.cache.CacheService;
import com.architecture.context.lock.LockService;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Objects;

/**
 * Redis配置
 *
 * @author luyi
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfiguration {

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    public RedissonClient redissonClient(RedisProperties redisProperties) {
        Config config;
        if (redisProperties != null && redisProperties.getConfig() != null) {
            config = redisProperties.getConfig();
        } else {
            config = new Config();
            config.setCodec(new JsonJacksonCodec());
            config.useSingleServer().setAddress("redis://localhost:6379");
        }
        return Redisson.create(config);
    }


    @Bean
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }


    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedissonConnectionFactory redisConnectionFactory) {
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    @Bean
    public RedisCacheManager redisCacheManager(RedisTemplate<String, Object> redisTemplate) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()));
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }


    @Bean
    public CacheService redisCacheServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        return new RedisCacheServiceImpl(redisTemplate);
    }

    @Bean("redisLock")
    public LockService lockService(RedissonClient redissonClient) {
        return new RedisLockServiceImpl(redissonClient);
    }

}
