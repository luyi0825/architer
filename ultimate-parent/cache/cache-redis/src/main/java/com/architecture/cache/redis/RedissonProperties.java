package com.architecture.cache.redis;

import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author luyi
 * redisson的属性配置
 */
@ConfigurationProperties(prefix = "customize.redisson")
public class RedissonProperties {
    /**
     * 是否启用
     */
    private boolean enabled = false;
    /**
     * redisson配置
     */
    private Config config;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
