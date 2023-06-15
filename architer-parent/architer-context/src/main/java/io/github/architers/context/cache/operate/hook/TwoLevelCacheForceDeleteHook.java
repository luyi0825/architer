package io.github.architers.context.cache.operate.hook;

import io.github.architers.context.cache.CacheProperties;
import io.github.architers.context.cache.model.BaseCacheParam;
import io.github.architers.context.cache.operate.CacheOperate;

/**
 * 两级缓存强制使用删
 * @author luyi
 */
public class TwoLevelCacheForceDeleteHook implements CacheOperateInvocationHook {

    private CacheProperties cacheProperties;
    @Override
    public boolean before(BaseCacheParam cacheOperationParam, CacheOperate cacheOperate) {
        cacheProperties.getCustomConfigs();
        return false;
    }

    @Override
    public void after(BaseCacheParam cacheOperationParam, CacheOperate cacheOperate) {

    }
}
