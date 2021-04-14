/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.lz.core.cache.redis;

import com.lz.core.cache.common.CacheProcess;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
public class RedisConfig {

    /**
     * 描述:配置单节点redission
     *
     * @author luyi
     * @date 2020/12/25 下午11:45
     */
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

    @Bean
   // @ConditionalOnBean(StringRedisTemplate.class)
    public StringRedisService stringRedisService(StringRedisTemplate redisTemplate) {
        return new StringRedisService(redisTemplate);
    }

    @Bean
//    @ConditionalOnMissingBean
//    @ConditionalOnBean(StringRedisService.class)
    public CacheProcess redisCacheCacheProcess() {
        return new RedisCacheCacheProcessImpl();
    }

}
