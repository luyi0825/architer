package com.architecture.context.cache;

import com.architecture.context.lock.LockEnum;
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
    /**
     * 使用什么锁
     */
    private LockEnum lockEnum = LockEnum.JDK;

    public LockEnum getLock() {
        return lockEnum;
    }

    public void setLock(LockEnum lockEnum) {
        this.lockEnum = lockEnum;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
