package com.lz.core.cache.common.operation;

import com.lz.core.cache.common.annotation.PutCache;
import com.lz.core.cache.common.key.ElExpressionKeyParser;
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

    private final ElExpressionKeyParser elExpressionKeyParser = new ElExpressionKeyParser();


    @Override
    protected Object executeCacheHandler(String key, CacheOperationMetadata metadata) {
        Object value = invoke(metadata);
        PutCacheOperation putCacheOperation = (PutCacheOperation) metadata.getCacheOperation();
        String cacheValue = putCacheOperation.getCacheValue();
        if (StringUtils.isEmpty(value)) {
            cacheManager.putCache(key, value, getKeyExpireTime(putCacheOperation));
        } else {
            Object needCacheValue = elExpressionKeyParser.generateKey(metadata, cacheValue);
            cacheManager.putCache(key, needCacheValue, getKeyExpireTime(putCacheOperation));
        }

        return value;
    }

    /**
     * 得到key过期时间
     *
     * @param operation PutCacheOperation
     * @return 过期的时间
     */
    private Long getKeyExpireTime(CacheOperation operation) {
        PutCacheOperation putCacheOperation = (PutCacheOperation) operation;
        return KeyExpireUtils.getExpireTime(putCacheOperation.getExpireTime(), putCacheOperation.getRandomExpireTime());
    }
}
