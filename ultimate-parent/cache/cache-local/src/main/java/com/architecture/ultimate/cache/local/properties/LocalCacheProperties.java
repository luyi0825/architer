package com.architecture.ultimate.cache.local.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author luyi
 * 本地缓存属性配置
 */
@ConfigurationProperties(prefix = "cache.local", ignoreInvalidFields = true)
public class LocalCacheProperties {

    /**
     * 自动清理时间，单位秒
     */
    private long autoClean = 10L;

    /**
     * 最大的数量
     */
    private long maximumSize = 1_000;

    /**
     * 初始化容量
     */
    private int initialCapacity = 5;


    public long getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(long maximumSize) {
        this.maximumSize = maximumSize;
    }

    public int getInitialCapacity() {
        return initialCapacity;
    }

    public void setInitialCapacity(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    public long getAutoClean() {
        return autoClean;
    }

    public void setAutoClean(long autoClean) {
        this.autoClean = autoClean;
    }
}
