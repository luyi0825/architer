package io.github.architers.context.cache;

import io.github.architers.context.cache.operate.CacheOperateFactory;
import io.github.architers.context.cache.operate.CacheOperationHandler;
import io.github.architers.context.cache.proxy.AnnotationCacheOperationSource;
import io.github.architers.context.cache.proxy.BeanFactoryCacheSourceAdvisor;
import io.github.architers.context.cache.proxy.CacheInterceptor;
import io.github.architers.context.expression.ExpressionParser;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Role;

import java.util.List;

/**
 * 缓存自动配置类
 *
 * @author luyi
 */
@Configuration(proxyBeanMethods = true)
@EnableConfigurationProperties(CacheProperties.class)
@Import(CacheOperateConfiguration.class)
public class CacheAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheAnnotationsParser cacheAnnotationsParser() {
        return new CacheAnnotationsParser();
    }


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AnnotationCacheOperationSource annotationCacheOperationSource() {
        return new AnnotationCacheOperationSource(cacheAnnotationsParser());
    }


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheInterceptor cacheInterceptor(List<CacheOperationHandler> cacheOperationHandlers, CacheAnnotationsParser cacheAnnotationsParser) {
        CacheInterceptor cacheInterceptor = new CacheInterceptor();
        cacheInterceptor.setCacheOperationHandlers(cacheOperationHandlers);
        cacheInterceptor.setCacheAnnotationsParser(cacheAnnotationsParser);
        return cacheInterceptor;
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

    @Bean
    public CacheOperateFactory cacheOperateFactory(CacheProperties cacheProperties) {
        return new CacheOperateFactory(cacheProperties.getDefaultCacheOperateClass());
    }

    @Bean
    public ExpressionParser expressionParser() {
        return new ExpressionParser();
    }
}
