package com.architecture.context.common.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 默认的缓存属性配置
 *
 * @author luyi
 */
@ConfigurationProperties(prefix = "customize.cache")
public class CacheProperties {
    /**
     * 缓存过期时间
     */
    private long expireTime;

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
