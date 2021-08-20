package com.architecture.context.cache.operation;


import com.architecture.context.cache.annotation.Cacheable;
import com.architecture.context.cache.operation.CacheOperationHandler;
import com.architecture.context.cache.operation.CacheOperationMetadata;
import com.architecture.context.common.cache.utils.CacheUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;


/**
 * CacheableOperation 对应的处理类
 *
 * @author luyi
 */
public class CacheableOperationHandler extends CacheOperationHandler {


    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof Cacheable;
    }

    @Override
    protected Object executeCacheHandler(String[] keys, CacheOperationMetadata metadata) {
        //从缓存中取值
        Object value = cacheManager.getCache(keys[0]);
        //缓存中没有值，就从数据库得到值或者解析值
        if (value == null) {
            CacheableOperation cacheableOperation = (CacheableOperation) metadata.getCacheOperation();
            String cacheValue = cacheableOperation.getCacheValue();
            long expireTime = CacheUtils.getExpireTime(cacheableOperation.getExpireTime(), cacheableOperation.getRandomExpireTime());
            //cacheValue为空，就将方法返回值作为缓存值
            if (StringUtils.isEmpty(cacheValue)) {
                value = invoke(metadata);
                Object finalValue = value;
                for (String key : keys) {
                    writeCache(cacheableOperation.isAsync(), () -> cacheManager.putCache(key, finalValue, expireTime));
                }
            } else {
                //说明给了默认的缓存值
                Object needCacheValue = cacheExpressionParser.executeParse(metadata, cacheValue);
                for (String key : keys) {
                    writeCache(cacheableOperation.isAsync(), () -> cacheManager.putCache(key, needCacheValue, expireTime));
                }
            }
        }
        return value;
    }
}
