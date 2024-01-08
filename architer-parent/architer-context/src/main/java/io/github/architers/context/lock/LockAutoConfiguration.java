package io.github.architers.context.lock;

import io.github.architers.context.lock.support.redission.RedissonLockFacotory;
import org.redisson.api.RedissonClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 锁的自动配置类
 *
 * @author luyi
 * @since 1.0.3
 */
@Configuration
@EnableConfigurationProperties(ConfigurationProperties.class)
public class LockAutoConfiguration {


    @Bean
    public RedissonLockFacotory redissonLockFacotory(RedissonClient redissonClient) {
        return new RedissonLockFacotory(redissonClient);
    }

}
