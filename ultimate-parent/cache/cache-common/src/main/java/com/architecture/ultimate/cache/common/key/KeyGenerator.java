package com.architecture.ultimate.cache.common.key;

import com.architecture.ultimate.cache.common.operation.CacheOperationMetadata;

/**
 * @author luyi
 * 缓存key 生成器
 */
public interface KeyGenerator {


    /**
     * 得到key
     *
     * @param cacheOperationMetadata 缓存操作元信息
     * @return 缓存的key
     */
    String getKey(CacheOperationMetadata cacheOperationMetadata);


}
