package io.github.architers.context.cache;

import io.github.architers.context.cache.operate.CacheNameWrapper;
import io.github.architers.context.cache.operate.CacheOperate;
import io.github.architers.context.cache.operate.LocalCacheOperate;
import io.github.architers.context.cache.operate.RemoteCacheOperate;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存属性配置
 *
 * @author luyi
 */
@ConfigurationProperties(prefix = "architers.cache")
@Data
public class CacheProperties {

    /**
     * 是否开启两级缓存
     */
    private boolean enableTwoLevelCache = false;

    /**
     * 是否延迟删(解决缓存一致性,默认没有开启）
     */
    private boolean defaultDelayEvictAgain = false;

    /**
     * 默认的延迟删的时间(默认5秒）
     */
    private long defaultDelayEvictMills = 5000L;
    /**
     * 缓存值改变路由标识
     */
    private String valueChangeRouteKey;

    /**
     * 默认的缓存操作处理器
     */
    private Class<? extends CacheOperate> defaultOperateClass;

    /**
     * 默认的本地缓存操作处理器
     */
    private Class<? extends LocalCacheOperate> defaultLocalOperateClass;


    /**
     * 默认的远程操作处理器
     */
    private Class<? extends RemoteCacheOperate> defaultRemoteOperateClass;


    /**
     * 默认的缓存名称包装器
     */
    private Class<? extends CacheNameWrapper> defaultCacheNameWrapperClass;

    /**
     * 缓存定制配置
     */
    private Map<String/*缓存名称*/, CacheConfig> customConfigs = new HashMap<>();





}
