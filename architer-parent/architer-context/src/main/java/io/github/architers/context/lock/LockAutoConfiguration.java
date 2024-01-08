package io.github.architers.context.lock;

import io.github.architers.context.lock.support.redission.RedissonLockFactory;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * 锁的自动配置类
 *
 * @author luyi
 * @since 1.0.3
 */
@Configuration
@EnableConfigurationProperties(ConfigurationProperties.class)
@Slf4j
public class LockAutoConfiguration implements ApplicationContextAware {


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RedissonClient redissonClient;

        try {
            //存在指定的锁的redisson客户端，就用指定的客户端
            redissonClient = applicationContext.getBean(LockBeanName.REDISSION_CLIENT, RedissonClient.class);
        } catch (Exception e) {
            redissonClient = applicationContext.getBean(RedissonClient.class);
            log.error("use default redissonClient");
        }
        RedissonLockFactory redissonLockFactory = new RedissonLockFactory(redissonClient);
        AbstractApplicationContext abstractApplicationContext = (AbstractApplicationContext) applicationContext;
        abstractApplicationContext.getBeanFactory().registerSingleton("redissonLockFactory", redissonLockFactory);
    }
}
