package io.github.architers.context.cache;

import io.github.architers.context.cache.operate.*;
import lombok.Data;

/**
 * 缓存配置
 *
 * @author luyi
 */
@Data
public class CacheConfig {

    /**
     * read后是否刷新缓存时间(默认为false)
     */
    private boolean readRefreshExpired = false;

    /**
     * 是否延迟删(解决缓存一致性）
     */
    private Boolean changeDelayDelete;

    /**
     * 定制的缓存操作处理器
     */
    private Class<? extends CacheOperate> operateClass;

    /**
     * 定制的本地缓存操作处理器
     */
    private Class<? extends LocalCacheOperate> localOperateClass;

    /**
     * 定制的远程操作处理器
     */
    private Class<? extends RemoteCacheOperate> remoteOperateClass;

    /**
     * 定制的缓存名称包装器
     */
    private Class<? extends CacheNameWrapper> cacheNameWrapperClass;

}
