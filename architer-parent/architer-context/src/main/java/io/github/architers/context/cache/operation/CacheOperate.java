package io.github.architers.context.cache.operation;


import io.github.architers.context.cache.Cache;
import io.github.architers.context.cache.annotation.PutCache;

/**
 * @author luyi
 * @version 1.0.0
 * 缓存管理接口类，用于获取不同缓存的实例对象
 */
public interface CacheOperate {

    public CacheOperate DEFAULT = new CacheOperate() {
    };

    /**
     * 设置缓存
     *
     * @param putCacheParam 设置缓存的参数
     */
    default void put(PutCacheParam putCacheParam) {

    }

    ;

    /**
     * 删除缓存
     */
    default void delete(DeleteCacheParam deleteCacheParam) {

    }


   default Object get(GetCacheParam getCacheParam) {
        return null;
    }

    /**
     * 清理所有
     */
    default void deleteAll(DeleteCacheParam deleteCacheParam){

    }
}
