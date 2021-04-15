package com.lz.core.cache.common;

/**
 * @author luyi
 * 注解缓存操作
 */
public interface AnnotationCacheOperation {

    Object getCache(String key);

    Object putCache(String key, Object value);

    void deleteCache(String key);

}
