package io.github.architers.context.cache;

import io.github.architers.context.cache.operate.CacheOperate;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 缓存属性配置
 *
 * @author luyi
 */
@ConfigurationProperties(prefix = "architers.cache")
@Data
public class CacheProperties {

    /**
     * 默认的缓存操作处理器
     */
    private Class<? extends CacheOperate> defaultCacheOperateClass;

    /**
     * 是否延迟双删(解决缓存一致性）
     */
    private boolean delayDoubleDelete = false;


}
