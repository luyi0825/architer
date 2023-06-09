package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.model.CacheOperationParam;

/**
 * 延迟删通知
 *
 * @author luyi
 */
public interface ICacheChangeNotify {

    void notify(CacheOperate cacheOperate, CacheOperationParam cacheOperationParam);

}
