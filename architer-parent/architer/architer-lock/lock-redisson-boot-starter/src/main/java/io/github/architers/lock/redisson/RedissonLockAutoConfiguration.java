package io.github.architers.lock.redisson;

import io.github.architers.propertconfig.redisson.RedissonBeanName;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;

import java.io.IOException;

/**
 * redisson分布式锁自动配置类
 *
 * @author luyi
 * @since 1.0.3
 */
@Configuration
@Slf4j
@EnableConfigurationProperties(RedissonLockProperties.class)
@ConditionalOnProperty(prefix = RedissonLockProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = false)
public class RedissonLockAutoConfiguration {


    /**
     * 如果锁使用的客户端隔离，而且不存在LOCK_BEAN_NAME，就注入新的RedissonClient
     */
    @Bean(destroyMethod = "shutdown", value = RedissonBeanName.LOCK_BEAN_NAME)
    @ConditionalOnMissingBean(name = RedissonBeanName.LOCK_BEAN_NAME)
    @ConditionalOnProperty(prefix = RedissonLockProperties.PREFIX, name = "isolation", havingValue = "true")
    public RedissonClient redissonLockClient(RedissonLockProperties redissonLockProperties) throws IOException {
        return redissonLockProperties.createClient();
    }

    /**
     * 创建redisson分布式锁工厂类
     * <li>获取redissonClient的情况不满足，就自己向容器中注入RedissonLockFactory</li>
     */
    @Bean
    @ConditionalOnMissingBean
    public RedissonLockFactory redissonLockFactory(AbstractApplicationContext applicationContext) {
        RedissonClient redissonClient;
        if (applicationContext.containsBean(RedissonBeanName.LOCK_BEAN_NAME)) {
            //存在指定的锁的redisson客户端，就用指定的客户端
            redissonClient = applicationContext.getBean(RedissonBeanName.LOCK_BEAN_NAME, RedissonClient.class);
        } else {
            try {
                //不存在指定的，就用已经存在的
                redissonClient = applicationContext.getBean(RedissonClient.class);
                log.info("use default redissonClient");
            } catch (NoSuchBeanDefinitionException e) {
                //一个都不存在，就根据属性配置一个（这个说明系统中redisson只作为分布式锁使用）
                redissonClient = applicationContext.getBean(RedissonLockProperties.class).createClient();
            }
        }
        return new RedissonLockFactory(redissonClient);
    }
}
