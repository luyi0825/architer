package com.architecture.context.cache;


import com.architecture.context.cache.operation.CacheableOperationHandler;
import com.architecture.context.cache.operation.DeleteCacheOperationHandler;
import com.architecture.context.cache.operation.PutCacheOperationHandler;
import com.architecture.context.expression.ExpressionParser;
import com.architecture.context.lock.LockFactory;
import com.architecture.context.lock.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
    public LockFactory lockFactory(List<LockService> lockServices) {
        LockFactory lockFactory = new LockFactory();
        lockFactory.setExpressionParser(new ExpressionParser());
        lockFactory.setLockServiceMap(null);
        return lockFactory;
    }

    @Bean
    public CacheableOperationHandler cacheableOperationHandler(LockFactory lockFactory) {
        CacheableOperationHandler cacheableOperationHandler = new CacheableOperationHandler();
        cacheableOperationHandler.setLockFactory(lockFactory);
        cacheableOperationHandler.setExpressionParser(new ExpressionParser());
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
