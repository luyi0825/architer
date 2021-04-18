package com.lz.core.cache.common;

/**
 * @author luyi
 * 注解缓存操作service接口层
 */
public interface CacheOperationService {

    /**
     * 得到缓存
     *
     * @param key 缓存的key
     * @return 缓存的数据
     */
    Object getCache(String key);

    /**
     * 放入缓存
     *
     * @param key   缓存的key
     * @param value 需要缓存的值
     * @return 同value
     */
    Object putCache(String key, Object value);

    /**
     * 删除缓存的值
     *
     * @param key 缓存的key
     */
    void deleteCache(String key);

}
