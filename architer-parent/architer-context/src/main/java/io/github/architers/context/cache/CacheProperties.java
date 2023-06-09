package io.github.architers.context.cache;

import io.github.architers.context.cache.operate.CacheOperate;
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
     * 缓存配置
     */
    private Map<String, CacheConfig> cacheConfigs = new HashMap<>();

    /**
     * 默认的缓存操作处理器
     */
    private Class<? extends CacheOperate> defaultCacheOperateClass;

    /**
     * 是否延迟删(解决缓存一致性）
     */
    private boolean delayDelete = true;




}
