package io.github.architers.context.cache.model;

import java.io.Serializable;

/**
 * 操作接口类
 *
 * @author luyi
 */
public interface CacheOperationParam extends Serializable {

    /**
     * 得到原始的缓存名称
     */
    String getOriginCacheName();


}
