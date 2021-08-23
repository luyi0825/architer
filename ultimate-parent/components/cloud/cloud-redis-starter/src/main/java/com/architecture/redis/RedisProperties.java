package com.architecture.redis;

import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author luyi
 * redis的属性配置
 */
@ConfigurationProperties(prefix = "customize.redis")
public class RedisProperties {
    /**
     * 配置
     */
    private Config config;

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
