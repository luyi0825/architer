package io.github.architers.context.cache.model;

import java.io.Serializable;

/**
 * 操作接口类
 *
 * @author luyi
 */
public interface CacheOperationParam extends Serializable {

    /**
     * 获取命名空间
     */
    default String getNamespace() {
        return null;
    }

    /**
     * 得到原始的缓存名称
     */
    String getOriginCacheName();


}
