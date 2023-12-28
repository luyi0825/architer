package io.github.architers.context.cache;

import io.github.architers.context.cache.enums.CacheLevel;
import io.github.architers.context.cache.operate.LocalCacheOperate;
import io.github.architers.context.cache.operate.RemoteCacheOperate;
import lombok.Data;

@Data
public class CacheConfigContext {

    /**
     * 缓存名称
     */
    private String cacheName;

    /**
     * 缓存级别
     */
    private CacheLevel cacheLevel;

    /**
     * 缓存值改变，延迟再删一次(解决缓存一致性）-->修改|删除都会再删一次
     */
    private Boolean changeDelayDeleteAgain;

    /**
     * 定制的本地缓存操作处理器
     */
    private LocalCacheOperate localOperateClass;

    /**
     * 定制的远程操作处理器
     */
    private RemoteCacheOperate remoteOperateClass;


}
