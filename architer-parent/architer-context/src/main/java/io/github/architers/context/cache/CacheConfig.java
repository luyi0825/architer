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
     * 缓存值改变，延迟再删一次(解决缓存一致性）-->修改|删除都会再删一次
     */
    private Boolean changeDelayDeleteAgain;

    /**
     * 定制的本地缓存操作处理器
     */
    private Class<? extends LocalCacheOperate> localOperateClass;

    /**
     * 定制的远程操作处理器
     */
    private Class<? extends RemoteCacheOperate> remoteOperateClass;


    /**
     *
     */


}
