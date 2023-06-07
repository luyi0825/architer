package io.github.architers.cache.caffeine;

/**
 * @author luyi
 * 驱逐策略
 */
public enum ExpireType {

    /**
     * 不过期
     */
    none,

    /**
     * 指定的时间
     */
    fix,

    /**
     *
     */
    expireAfterAccess,
    expireAfterWrite,

    expireAfter;


}
