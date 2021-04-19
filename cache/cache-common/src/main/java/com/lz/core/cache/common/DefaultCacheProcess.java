package com.lz.core.cache.common;


import com.lz.core.cache.common.operation.CacheOperation;
import com.lz.core.cache.common.operation.CacheOperationHandler;
import org.springframework.beans.factory.annotation.Autowired;


import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

/**
 * @author luyi
 * 缓存处理抽象类
 * @TODO 当一个类存在多个注解怎么处理
 */
public class DefaultCacheProcess implements CacheProcess {


    private List<CacheOperationHandler> cacheOperationHandlers;


    @Override
    public Object process(Object target, Method method, Object[] args, Collection<CacheOperation> cacheOperations) {
        try {
            for (CacheOperation operation : cacheOperations) {
                for (CacheOperationHandler cacheOperationHandler : cacheOperationHandlers) {
                    if(cacheOperationHandler.match(operation.getAnnotation())){
                        return cacheOperationHandler.handler(target, method, args, operation);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("cache process error", e);
        }
        return null;
    }



    @Autowired(required = false)
    public void setCacheOperationHandlers(List<CacheOperationHandler> cacheOperationHandlers) {
        this.cacheOperationHandlers = cacheOperationHandlers;
    }
}
