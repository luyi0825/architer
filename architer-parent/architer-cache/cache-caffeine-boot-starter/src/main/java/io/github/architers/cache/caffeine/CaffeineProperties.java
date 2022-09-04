package io.github.architers.cache.caffeine;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author luyi
 * Caffeine属性配置
 */
@ConfigurationProperties("architer.cahce.caffeine")
public class CaffeineProperties {
    /**
     * 驱逐策略
     */
    private EvictionStrategy evictionStrategy;
}
