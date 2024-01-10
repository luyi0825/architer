package io.github.architers.context.cache.enums;


/**
 * 缓存类型
 *
 * @author luyi
 */

public enum CacheLevel {

    /**
     * 不需要缓存
     */
    none,

    /**
     * 本地缓存
     */
    local,
    /**
     * 远程缓存
     */
    remote,
    /**
     * 两级缓存
     */
    localAndRemote;


    public static boolean isContainLocal(CacheLevel cacheLevel) {
        if (cacheLevel == null) {
            return false;
        }
        return CacheLevel.local.equals(cacheLevel) || CacheLevel.localAndRemote.equals(cacheLevel);
    }

}
