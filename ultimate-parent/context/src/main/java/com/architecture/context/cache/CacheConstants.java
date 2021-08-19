package com.architecture.context.cache;


/**
 * 描述:缓存的常量
 *
 * @author luyi
 * @date 2020/12/24 下午11:20
 */
public class CacheConstants {
    /**
     * 缓存不过期
     */
    public static final long NEVER_EXPIRE = -1;
    /**
     * 默认缓存失效的时间：12小时
     */
    public static final long DEFAULT_CACHE_EXPIRE_TIME = 60 * 60 * 12;
}
