package com.architecture.ultimate.cache.common;


/**
 * 描述:缓存的常量
 *
 * @author luyi
 * @date 2020/12/24 下午11:20
 */
public class Constants {
    /**
     * 缓存不过期
     */
    public static final long NEVER_EXPIRE = -1;
    /**
     * 缓存不存在放0,防止缓存穿透
     */
    public static final String CACHE_NOT_EXIST = "0";
}
