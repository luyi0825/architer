package io.github.architers.context.cache.spi;

import io.github.architers.context.cache.operation.CacheOperate;

/**
 * 动态缓存命名空间获取
 *
 * @author luyi
 */
public interface DynamicNameSpace {

    /**
     * 通过缓存管理空间获取缓存空间
     *
     * @param cacheOperate 缓存管理器
     * @return namespace
     */
    String get(CacheOperate cacheOperate);

}
