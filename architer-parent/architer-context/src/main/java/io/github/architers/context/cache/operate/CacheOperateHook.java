package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.model.BaseCacheParam;

/**
 * @author luyi
 */
public interface CacheOperateHook {


    /**
     * 操作之前
     *
     * @param cacheOperationParam 缓存值为空
     */
    boolean before(BaseCacheParam cacheOperationParam, CacheOperate cacheOperate);

    void end(BaseCacheParam cacheOperationParam, CacheOperate cacheOperate);
}
