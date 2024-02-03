package io.github.architers.context.cache;

import io.github.architers.context.cache.operate.LocalCacheOperate;
import io.github.architers.context.cache.operate.RemoteCacheOperate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

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
public class CacheProperties implements Serializable {

    /**
     * 命名空间
     */
    private String namespace;

    /**
     * 公共的缓存配置
     */
    @NestedConfigurationProperty
    private CacheConfig commonConfig;

    /**
     * 缓存定制配置
     */
    private Map<String/*缓存名称*/, CacheConfig> customConfigs = new HashMap<>();

}
