package com.architecture.context.cache.operation;


import com.architecture.context.cache.annotation.PutCache;
import com.architecture.context.common.cache.utils.CacheUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;


/**
 * 对应PutCacheOperation
 *
 * @author luyi
 */
public class PutCacheOperationHandler extends CacheOperationHandler {
    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof PutCache;
    }

    @Override
    protected Object executeCacheHandler(String[] keys, CacheOperationMetadata metadata) {
        Object value = invoke(metadata);
        PutCacheOperation putCacheOperation = (PutCacheOperation) metadata.getCacheOperation();
        String cacheValue = putCacheOperation.getCacheValue();
        long expireTime = CacheUtils.getExpireTime(putCacheOperation.getExpireTime(), putCacheOperation.getRandomExpireTime());
        for (String key : keys) {
            if (StringUtils.isEmpty(cacheValue)) {
               // writeCache(putCacheOperation.isAsync(), () -> cacheService.putCache(key, value, expireTime));
            } else {
                Object needCacheValue = cacheExpressionParser.executeParse(metadata, cacheValue);
                //  writeCache(putCacheOperation.isAsync(), () -> cacheService.putCache(key, needCacheValue, expireTime));
            }
        }
        return value;
    }

}
