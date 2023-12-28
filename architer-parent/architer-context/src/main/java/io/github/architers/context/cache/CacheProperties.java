package io.github.architers.context.cache;

import io.github.architers.context.cache.operate.CacheNameWrapper;
import io.github.architers.context.cache.operate.LocalCacheOperate;
import io.github.architers.context.cache.operate.RemoteCacheOperate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 缓存属性配置
 *
 * @author luyi
 */
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "architers.cache")
@Data
public class CacheProperties extends CacheConfig implements InitializingBean, Serializable {
    /**
     * 命名空间
     */
    private String namespace;
    /**
     * 默认的本地缓存操作处理器
     */
    private Class<? extends LocalCacheOperate> localOperateClass;

    /**
     * 默认的远程操作处理器
     */
    private Class<? extends RemoteCacheOperate> remoteOperateClass;

    /**
     * 缓存值改变，延迟再删一次(解决缓存一致性）-->修改|删除都会再删一次
     */
    private Boolean changeDelayDeleteAgain;

    /**
     * 默认的缓存名称包装器
     */
    private Class<? extends CacheNameWrapper> cacheNameWrapperClass;

    /**
     * 缓存定制配置
     */
    private Map<String/*缓存名称*/, CacheConfig> customConfigs = new HashMap<>();

    /**
     * 动态元数据配置
     */
    private Map<String, DyMetaDataConfig> dyMetaDataConfigs = new HashMap<>();


    @Override
    public void afterPropertiesSet() {

    }
}
