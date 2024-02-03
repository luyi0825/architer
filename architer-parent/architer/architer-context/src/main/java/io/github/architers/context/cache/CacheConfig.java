package io.github.architers.context.cache;

import io.github.architers.context.cache.operate.*;
import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

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

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 过期的时间单位
     */
    private TimeUnit expireUnit = TimeUnit.SECONDS;

    /**
     * 随机时间
     */
    private long expireRandomTime = 0;

    /**
     * 缓存值改变，延迟再删一次(解决缓存一致性）-->修改|删除都会再删一次
     */
    private Boolean changeDelayDeleteAgain;

    /**
     * 延迟时间(默认30秒）
     */
    private Long delayDeleteTime = 30L;


}
