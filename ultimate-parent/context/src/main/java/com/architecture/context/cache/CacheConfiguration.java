package com.architecture.context.cache;


import com.architecture.context.cache.lock.LockExecute;
import com.architecture.context.cache.operation.CacheableOperationHandler;
import com.architecture.context.cache.operation.DeleteCacheOperationHandler;
import com.architecture.context.cache.operation.PutCacheOperationHandler;
import com.architecture.context.expression.ExpressionParser;
import com.architecture.context.cache.lock.LockFactory;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author luyi
 * 缓存配置，存放各种缓存的公共配置
 */
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class CacheConfiguration {


    @Bean
    public ExpressionParser keyExpressionParser() {
        return new ExpressionParser();
    }

    @Bean
    public LockFactory lockFactory(ExpressionParser expressionParser) {
        LockFactory lockFactory = new LockFactory();
        lockFactory.setExpressionParser(expressionParser);
        return lockFactory;
    }

    @Bean
    public LockExecute lockExecute(LockFactory lockFactory) {
        return new LockExecute(lockFactory);
    }

    @Bean
    public CacheableOperationHandler cacheableOperationHandler(ExpressionParser expressionParser) {
        CacheableOperationHandler cacheableOperationHandler = new CacheableOperationHandler();
        cacheableOperationHandler.setExpressionParser(expressionParser);
        return cacheableOperationHandler;
    }

    @Bean
    public DeleteCacheOperationHandler deleteCacheOperationHandler() {
        return new DeleteCacheOperationHandler();
    }

    @Bean
    public PutCacheOperationHandler putCacheOperationHandler() {
        return new PutCacheOperationHandler();
    }


}
