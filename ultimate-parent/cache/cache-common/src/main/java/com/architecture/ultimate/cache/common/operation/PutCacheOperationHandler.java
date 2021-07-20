package com.architecture.ultimate.cache.common.operation;


import com.architecture.ultimate.cache.common.annotation.PutCache;
import com.architecture.ultimate.cache.common.utils.CacheUtils;
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
    protected Object executeCacheHandler(String key, CacheOperationMetadata metadata) {
        Object value = invoke(metadata);
        PutCacheOperation putCacheOperation = (PutCacheOperation) metadata.getCacheOperation();
        String cacheValue = putCacheOperation.getCacheValue();
        long expireTime = CacheUtils.getExpireTime(putCacheOperation.getExpireTime(), putCacheOperation.getRandomExpireTime());
        if (StringUtils.isEmpty(cacheValue)) {
            writeCache(putCacheOperation.isAsync(), () -> cacheManager.putCache(key, value, expireTime));
        } else {
            Object needCacheValue = cacheExpressionParser.executeParse(metadata, cacheValue);
            writeCache(putCacheOperation.isAsync(), () -> cacheManager.putCache(key, needCacheValue, expireTime));
        }
        return value;
    }


}
