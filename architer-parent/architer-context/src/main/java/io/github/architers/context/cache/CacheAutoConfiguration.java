package io.github.architers.context.cache;


import io.github.architers.context.cache.operation.*;
import io.github.architers.context.expression.ExpressionParser;
import io.github.architers.context.lock.LockFailService;
import io.github.architers.context.lock.Locked;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 * 缓存配置，存放各种缓存的公共配置
 */
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class CacheAutoConfiguration {


    @Bean
    public ExpressionParser keyExpressionParser() {
        return new ExpressionParser();
    }


    @Bean
    @ConditionalOnMissingBean
    public CacheableOperationHandler cacheableOperationHandler(ExpressionParser expressionParser) {
        CacheableOperationHandler cacheableOperationHandler = new CacheableOperationHandler();
        cacheableOperationHandler.setExpressionParser(expressionParser);
        return cacheableOperationHandler;
    }

    @Bean
    public DefaultkeyGenerator defaultkeyGenerator() {
        return new DefaultkeyGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    public CacheOperateFactory cacheCacheOperateFactory(io.github.architers.context.cache.CacheProperties cacheProperties) {
        return new CacheOperateFactory(cacheProperties.getDefaultCacheOperateClass());
    }

    @Bean
    @ConditionalOnMissingBean
    public KeyGeneratorFactory keyGeneratorFactory() {
        return new KeyGeneratorFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public DeleteCacheOperationHandler deleteCacheOperationHandler() {
        return new DeleteCacheOperationHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public PutCacheOperationHandler putCacheOperationHandler() {
        return new PutCacheOperationHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public BatchDeleteOperationHandler batchDeleteOperationHandler() {
        return new BatchDeleteOperationHandler();
    }

    @Bean
    @ConditionalOnMissingBean

    public BatchPutOperationHandler batchPutOperationHandler() {
        return new BatchPutOperationHandler();
    }


}
