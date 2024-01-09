package io.github.architers.context.lock;

import io.github.architers.common.expression.method.ExpressionParser;
import io.github.architers.context.lock.proxy.*;
import io.github.architers.context.lock.support.redission.RedissonLockFactory;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * 锁的自动配置类
 *
 * @author luyi
 * @since 1.0.3
 */
@Configuration
@EnableConfigurationProperties(LockProperties.class)
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
            log.info("use default redissonClient");
        }
        RedissonLockFactory redissonLockFactory = new RedissonLockFactory(redissonClient);
        AbstractApplicationContext abstractApplicationContext = (AbstractApplicationContext) applicationContext;
        abstractApplicationContext.getBeanFactory().registerSingleton("redissonLockFactory", redissonLockFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    public ExpressionParser expressionParser() {
        return new ExpressionParser();
    }

    @Bean
    public LockAnnotationsParser lockAnnotationsParser() {
        return new LockAnnotationsParser();
    }

    @Bean
    public LockProxySupport lockSupport() {
        return new LockProxySupport();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LockAnnotationOperationSource lockAnnotationOperationSource(LockAnnotationsParser lockAnnotationsParser) {
        return new LockAnnotationOperationSource(lockAnnotationsParser);
    }


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LockInterceptor cacheInterceptor(LockProxySupport lockProxySupport) {
        return new LockInterceptor(lockProxySupport);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LockBeanFactorySourceAdvisor lockBeanFactorySourceAdvisor(LockInterceptor lockInterceptor,
                                                                     LockAnnotationOperationSource lockAnnotationOperationSource) {
        LockBeanFactorySourceAdvisor lockBeanFactorySourceAdvisor = new LockBeanFactorySourceAdvisor();
        lockBeanFactorySourceAdvisor.setLockOperationSource(lockAnnotationOperationSource);
        lockBeanFactorySourceAdvisor.setAdvice(lockInterceptor);
        return lockBeanFactorySourceAdvisor;
    }


}
