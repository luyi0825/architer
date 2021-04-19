package com.lz.core.cache.common.proxy;

import com.lz.core.cache.common.AnnotationCacheOperation;
import com.lz.core.cache.common.CacheProcess;
import com.lz.core.cache.common.key.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cache.annotation.AnnotationCacheOperationSource;
import org.springframework.cache.interceptor.BeanFactoryCacheOperationSourceAdvisor;
import org.springframework.cache.interceptor.CacheOperationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.lang.Nullable;

import java.util.function.Supplier;

/**
 * @author zhoupei
 * @create 2021/4/13
 **/
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class ProxyConfiguration extends  AbstractCacheConfiguration{

    private CacheProcess cacheProcess;


    @Bean(name = "com.lz.cache.common.cacheAdvisor")
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BeanFactoryCacheOperationSourceAdvisor cacheAdvisor() {
        BeanFactoryCacheOperationSourceAdvisor advisor = new BeanFactoryCacheOperationSourceAdvisor();
        advisor.setCacheOperationSource(cacheOperationSource());
        advisor.setAdvice(cacheInterceptor());
        return advisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheOperationSource cacheOperationSource() {
        return new AnnotationCacheOperationSource();
    }
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CachingInterceptor cacheInterceptor() {
        CachingInterceptor interceptor = new CachingInterceptor(this.cacheProcess);
//        interceptor.setCacheOperationSources(cacheOperationSource());
        return interceptor;
    }

    @Autowired
    public void setCacheProcess(CacheProcess cacheProcess) {
        this.cacheProcess = cacheProcess;
    }
//    @Autowired
//    public void setKeyGenerator(KeyGenerator keyGenerator) {
//        this.keyGenerator = keyGenerator;
//    }
//
//    @Autowired
//    public void setCacheOperation(@Nullable Supplier<AnnotationCacheOperation> cacheOperation) {
//        this.cacheOperation = cacheOperation;
//    }
}
