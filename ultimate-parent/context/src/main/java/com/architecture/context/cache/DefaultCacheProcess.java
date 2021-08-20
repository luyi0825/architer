package com.architecture.context.cache;


import com.architecture.context.cache.operation.CacheOperationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.CacheOperation;
import org.springframework.util.CollectionUtils;


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
            if (!CollectionUtils.isEmpty(cacheOperations)) {
                Object value = null;
                for (CacheOperation operation : cacheOperations) {
                    for (CacheOperationHandler cacheOperationHandler : cacheOperationHandlers) {
                        if (cacheOperationHandler.match(operation.getAnnotation())) {
                            CacheOperationMetadata cacheOperationMetadata = new CacheOperationMetadata(operation, target, method, args);
                            Object fistValue = cacheOperationHandler.handler(cacheOperationMetadata);
                            //存在多个注解的时候，将第一个有返回值的注解作作为返回值
                            if (fistValue != null) {
                                value = fistValue;
                            }
                        }
                    }
                }
                return value;
            }
            return method.invoke(target, args);
        } catch (Exception e) {
            throw new RuntimeException("cache process error", e);
        }

    }


    @Autowired(required = false)
    public void setCacheOperationHandlers(List<CacheOperationHandler> cacheOperationHandlers) {
        this.cacheOperationHandlers = cacheOperationHandlers;
    }
}
