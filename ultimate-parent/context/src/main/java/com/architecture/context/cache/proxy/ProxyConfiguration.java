package com.architecture.context.cache.proxy;


import com.architecture.context.cache.CacheAnnotationsParser;
import com.architecture.context.cache.CacheProcess;
import com.architecture.context.cache.DefaultCacheProcess;
import org.springframework.beans.factory.config.BeanDefinition;

import org.springframework.context.annotation.AutoProxyRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * @author zhoupei
 * @author luyi 增加缓存advice处理
 * @create 2021/4/13
 **/
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class ProxyConfiguration {

    @Bean
    public AutoProxyRegistrar autoProxyRegistrar() {
        return new AutoProxyRegistrar();
    }


    @Bean
    public CacheAnnotationsParser cacheAnnotationsParser() {
        return new CacheAnnotationsParser();
    }

    @Bean
    public AnnotationCacheOperationSource annotationCacheOperationSource() {
        return new AnnotationCacheOperationSource(cacheAnnotationsParser());
    }

    @Bean
    public CacheProcess cacheProcess() {
        return new DefaultCacheProcess();
    }

    @Bean
    public CacheInterceptor cacheInterceptor(CacheProcess cacheProcess, CacheAnnotationsParser cacheAnnotationsParser) {
        CacheInterceptor cacheInterceptor = new CacheInterceptor();
        cacheInterceptor.setCacheProcess(cacheProcess);
        cacheInterceptor.setCacheAnnotationsParser(cacheAnnotationsParser);
        return cacheInterceptor;
    }

    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public BeanFactoryCacheSourceAdvisor beanFactoryCacheSourceAdvisor(AnnotationCacheOperationSource annotationCacheOperationSource, CacheInterceptor cacheInterceptor) {
       BeanFactoryCacheSourceAdvisor advisor = new BeanFactoryCacheSourceAdvisor();
        advisor.setCacheOperationSource(annotationCacheOperationSource);
        advisor.setAdvice(cacheInterceptor);
        return advisor;
    }
}
