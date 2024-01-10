package io.github.architers.context.lock;

import io.github.architers.common.expression.method.ExpressionParser;
import io.github.architers.context.lock.proxy.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * 锁的自动配置类
 *
 * @author luyi
 * @since 1.0.3
 */
@Configuration
@EnableConfigurationProperties(LockProperties.class)
public class LockAutoConfiguration {


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
