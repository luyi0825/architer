package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.model.BaseCacheParam;

public interface CacheOperateEndHook {

    boolean start(BaseCacheParam cacheOperationParam, CacheOperate cacheOperate);

    void end(BaseCacheParam cacheOperationParam, CacheOperate cacheOperate);
}
