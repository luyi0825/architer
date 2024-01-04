package io.github.architers.context.cache.fieldconvert;

import java.util.Map;

/**
 * 临时缓存
 *
 * @author luyi
 */
public interface TempCache {

    /**
     * 临时缓存是否能够驱逐（淘汰策略能驱逐缓存数据）
     */
    boolean isCanEvict();

    /**
     * 从临时缓存中获取值
     */
    Object get(String key);

    /**
     * 向临时缓存放入值
     *
     * @param key   缓存key
     * @param value 缓存值
     */
    void put(String key, Object value);

    /**
     * 批量放置缓存
     */
    void batchPut(Map<String, Object> value);

    boolean containsKey(String key);
}
