package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.model.BaseCacheParam;

public interface CacheOperateEndHook {
    void end(BaseCacheParam cacheOperationParam, CacheOperate cacheOperate);
}
