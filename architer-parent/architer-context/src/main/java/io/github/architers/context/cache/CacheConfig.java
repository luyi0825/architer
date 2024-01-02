package io.github.architers.context.cache;

import io.github.architers.context.cache.enums.CacheLevel;
import io.github.architers.context.cache.operate.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 缓存配置
 *
 * @author luyi
 */
@Data
public class CacheConfig implements Serializable {

    /**
     * 定制的本地缓存操作处理器
     */
    private Class<? extends LocalCacheOperate> localOperateClass;

    /**
     * 定制的远程操作处理器
     */
    private Class<? extends RemoteCacheOperate> remoteOperateClass;




}
