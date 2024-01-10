package io.github.architers.context.cache;

import io.github.architers.context.cache.operate.LocalCacheOperate;
import io.github.architers.context.cache.operate.RemoteCacheOperate;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
public class CacheProperties extends CacheConfig  implements Serializable {
    /**
     * 命名空间
     */
    private String namespace;

    /**
     * 缓存定制配置
     */
    private Map<String/*缓存名称*/, CacheConfig> customConfigs = new HashMap<>();

}
