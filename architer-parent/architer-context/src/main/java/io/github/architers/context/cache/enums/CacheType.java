package io.github.architers.context.cache.enums;

import io.github.architers.context.cache.operate.CacheOperate;
import io.github.architers.context.cache.operate.LocalCacheOperate;
import io.github.architers.context.cache.operate.RemoteCacheOperate;

/**
 * 缓存类型
 *
 * @author luyi
 */

public enum CacheType {

    /**
     * 本地缓存
     */
    local,
    /**
     * 远程缓存
     */
    remote,
    /**
     * 两级缓存
     */
    twoLevel;

    public static CacheType getCacheType(CacheOperate cacheOperate) {
        if (cacheOperate instanceof LocalCacheOperate) {
            return local;
        }
        if (cacheOperate instanceof RemoteCacheOperate) {
            return remote;
        }
        return twoLevel;
    }
}
