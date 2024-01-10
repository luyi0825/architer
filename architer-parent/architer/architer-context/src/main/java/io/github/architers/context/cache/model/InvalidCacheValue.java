package io.github.architers.context.cache.model;

import java.io.Serializable;

/**
 * 空值
 * <li>防止缓存穿透用的类</li>
 * <li>防止重复查询</li>
 *
 * @author luyi
 * @version 1.0.0
 */
public class InvalidCacheValue implements Serializable {
    public static final InvalidCacheValue INVALID_CACHE = new InvalidCacheValue();
    private String value = "INVALID_CACHE";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
