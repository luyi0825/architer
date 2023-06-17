package io.github.architers.context.cache.operate;

import lombok.Data;

@Data
public class CacheOperateContext {

    /**
     * 缓存名称包装器
     */
    private CacheNameWrapper cacheNameWrapper;

    /**
     * 缓存操作器
     */
    private CacheOperate cacheOperate;


    /**
     * 是否延迟双删
     */
    private boolean delayEvict;

    /**
     * 延迟删除时间(毫秒）
     */
    private long delayEvictMills;
}
