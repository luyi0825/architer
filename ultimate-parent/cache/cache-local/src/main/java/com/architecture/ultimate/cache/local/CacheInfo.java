package com.architecture.ultimate.cache.local;

/**
 * @author luyi
 * 缓存信息
 * 过期时间
 */
public class CacheInfo {
    /**
     * 数据
     */
    private Object data;
    /**
     * 过期时间
     */
    private Long expireDate;

    public CacheInfo(Object data, Long expireDate) {
        this.data = data;
        this.expireDate = expireDate;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Long expireDate) {
        this.expireDate = expireDate;
    }
}
