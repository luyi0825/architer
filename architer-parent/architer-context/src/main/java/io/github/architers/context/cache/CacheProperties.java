package io.github.architers.context.cache;

import io.github.architers.context.cache.operate.CacheNameWrapper;
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
     * 缓存定制配置
     */
    private Map<String/*缓存名称*/, CacheConfig> customConfigs = new HashMap<>();

    /**
     * 默认的缓存名称包装器
     */
    private Class<? extends CacheNameWrapper> cacheNameWrapperClass;




    @Override
    public void afterPropertiesSet() {
        CacheProperties cacheProperties = this;
        new Thread(() -> {
            CacheConfigManager.setDefaultCacheConfig(cacheProperties);
            cacheProperties.customConfigs.forEach(CacheConfigManager::propertyOverDefault);
        }).start();
    }
}
