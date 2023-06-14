package io.github.architers.context.cache.proxy;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

public class ProxyCacheConfiguration {
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AnnotationCacheOperationSource annotationCacheOperationSource(CacheAnnotationsParser cacheAnnotationsParser) {
        return new AnnotationCacheOperationSource(cacheAnnotationsParser);
    }


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheInterceptor cacheInterceptor(CacheProxySupport cacheProxySupport) {
        return new CacheInterceptor(cacheProxySupport);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BeanFactoryCacheSourceAdvisor beanFactoryCacheSourceAdvisor(AnnotationCacheOperationSource annotationCacheOperationSource,
                                                                       CacheInterceptor cacheInterceptor) {
        BeanFactoryCacheSourceAdvisor advisor = new BeanFactoryCacheSourceAdvisor();
        advisor.setCacheOperationSource(annotationCacheOperationSource);
        advisor.setAdvice(cacheInterceptor);
        return advisor;
    }
}
