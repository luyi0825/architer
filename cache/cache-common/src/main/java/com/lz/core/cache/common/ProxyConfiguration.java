package com.lz.core.cache.common;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cache.annotation.AbstractCachingConfiguration;
import org.springframework.cache.interceptor.BeanFactoryCacheOperationSourceAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * @author zhoupei
 * @create 2021/4/13
 **/
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class ProxyConfiguration extends AbstractCachingConfiguration {

    @Bean(name = "com.lz.cache.common.cacheAdvisor")
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BeanFactoryCacheOperationSourceAdvisor cacheAdvisor() {
        BeanFactoryCacheOperationSourceAdvisor advisor = new BeanFactoryCacheOperationSourceAdvisor();
        return advisor;
    }

}
