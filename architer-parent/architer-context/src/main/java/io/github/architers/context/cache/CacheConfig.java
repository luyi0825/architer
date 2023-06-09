package io.github.architers.context.cache;

import io.github.architers.context.cache.operate.CacheOperate;
import io.github.architers.context.cache.operate.DefaultCacheOperate;
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
    private boolean delayDelete = true;

    /**
     * 缓存操作处理器
     */
    private Class<? extends CacheOperate> cacheOperateClass = DefaultCacheOperate.class;

}
