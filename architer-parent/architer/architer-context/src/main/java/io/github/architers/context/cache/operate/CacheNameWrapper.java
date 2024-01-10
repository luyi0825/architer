package io.github.architers.context.cache.operate;


import io.github.architers.common.expression.method.ExpressionMetadata;

/**
 * 缓存名称包装，可以在缓存名称前边进行装饰
 *
 * @author luyi
 */
public interface CacheNameWrapper {

    /**
     * 获取缓存名称
     *
     * @param oldCacheName 原来的缓存名称
     * @return 装饰后缓存名称
     */
    String getCacheName(ExpressionMetadata expressionMetadata, String oldCacheName);
}
