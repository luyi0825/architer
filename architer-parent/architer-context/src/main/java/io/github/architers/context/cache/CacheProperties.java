package io.github.architers.context.cache;

import io.github.architers.context.cache.operate.LocalCacheOperate;
import io.github.architers.context.cache.operate.RemoteCacheOperate;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 缓存属性配置
 *
 * @author luyi
 */
@ConfigurationProperties(prefix = "architers.cache")
@Data
public class CacheProperties  implements Serializable {
    /**
     * 命名空间
     */
    private String namespace;
    /**
     * 默认的本地缓存操作处理器
     */
    private Class<? extends LocalCacheOperate> defalutLocalOperateClass;

    /**
     * 默认的远程操作处理器
     */
    private Class<? extends RemoteCacheOperate> defalutRemoteOperateClass;

    /**
     * 缓存值改变，延迟再删一次(解决缓存一致性）-->修改|删除都会再删一次
     */
    private Boolean changeDelayDeleteAgain;

    /**
     * 缓存定制配置
     */
    private Map<String/*缓存名称*/, CacheConfig> customConfigs = new HashMap<>();

}
