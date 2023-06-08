package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.model.CacheOperationParam;

/**
 * @author Administrator
 */
public interface CacheOperateHook {

    void end(CacheOperationParam cacheOperationParam);
}
