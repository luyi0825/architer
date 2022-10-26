package io.github.architers.context.cache.operation;

/**
 * 缓存key装饰,方便key拓展
 *
 * @author luyi
 */
public interface CacheKeyWrapper {

    /**
     * 获取装饰后的缓存key
     *
     * @param cacheOperate   缓存管理器
     * @param originCacheKey 原来的缓存key
     * @return 装饰后，真正的缓存key
     */
    String getCacheKey(CacheOperate cacheOperate, String originCacheKey);
}
