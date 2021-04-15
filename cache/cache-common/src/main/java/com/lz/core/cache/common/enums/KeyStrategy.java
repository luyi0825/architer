package com.lz.core.cache.common.enums;

/**
 * @author luyi
 * key的策略，判断是通过参数还是注解拼接key
 * 这个字段只影响::后边的部分
 */
public enum KeyStrategy {
    /**
     * 注解
     *
     * @see com.lz.core.cache.common.annotation.CacheKey
     */
    ANNOTATION(),
    /**
     * 参数
     */
    PARAM,
    /**
     * 没有，给Cacheable、PutCache,DeleteCache做默认值
     */
    NONE

}
