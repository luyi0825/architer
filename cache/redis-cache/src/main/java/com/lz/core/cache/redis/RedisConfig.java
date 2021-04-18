/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.lz.core.cache.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis配置
 *
 * @author luyi
 */
@Configuration
public class RedisConfig {
    @Bean
    @Autowired(required = false)
    public StringRedisService stringRedisService(StringRedisTemplate redisTemplate) {
        return new StringRedisService(redisTemplate);
    }

    @Bean
    public RedisCacheOperationService redisAnnotationCacheOperation() {
        return new RedisCacheOperationService();
    }

}
