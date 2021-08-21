package com.architecture.context.cache;


import org.springframework.lang.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 * 注解缓存操作service接口层
 */
public interface CacheService {

    /**
     * 描述:向redis中放入值:永不过期
     *
     * @param key   缓存的key
     * @param value 缓存的值
     */
    void set(String key, Object value);

    /**
     * 描述:向缓存中存放值
     *
     * @param expire   过期时间
     * @param timeUnit 单位
     * @param key      缓存的key
     * @param value    缓存的值
     */
    void set(String key, Object value, long expire, TimeUnit timeUnit);


    /**
     * 描述:如果不存在，就向缓存中设置值(设置的值不过期)
     *
     * @param key   缓存的key
     * @param value 缓存的值
     * @return true为设置成功
     */
    boolean setIfAbsent(String key, Object value);

    /**
     * 描述:如果不存在，就向缓存中设置值
     *
     * @param expire   过期的时间
     * @param key      缓存的key
     * @param value    缓存的值
     * @param timeUnit 单位
     * @return 是否设置成功：true成功
     */
    boolean setIfAbsent(String key, Object value, long expire, TimeUnit timeUnit);

    /**
     * 描述:如果不存在，就向缓存中设置值
     *
     * @param key   缓存的key
     * @param value 缓存的值
     * @return 是否设置成功：true成功
     */
    boolean setIfPresent(String key, Object value);

    /**
     * 描述:如果存在，就向缓存中设置值
     *
     * @param expire   过期的时间
     * @param key      缓存的key
     * @param value    缓存的值
     * @param timeUnit 单位
     * @return 是否成功
     */
    boolean setIfPresent(String key, Object value, long expire, TimeUnit timeUnit);

    //*************************************get**************************************************/

    /**
     * 得到以前的值，并设置新的值
     *
     * @param key   缓存的key
     * @param value 缓存的值
     * @return 原来的缓存值
     */
    Object getAndSet(String key, Object value);

    /**
     * 描述:得到缓存值
     *
     * @param key 缓存的key
     * @return 缓存的值
     */
    Object get(String key);

    /**
     * 只适合指定类型的值
     *
     * @param key   缓存的key
     * @param clazz 值的类型
     * @return 缓存后T类型的数据
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 描述:删除缓存
     *
     * @param key 缓存的key
     */
    void delete(String key);
}
