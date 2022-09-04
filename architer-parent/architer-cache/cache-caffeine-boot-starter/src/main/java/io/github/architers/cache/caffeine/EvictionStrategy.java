package io.github.architers.cache.caffeine;

/**
 * @author luyi
 * 驱逐策略
 */
public enum EvictionStrategy {
    /**
     * 随机
     */
    random,
    /**
     * 过期
     */
    EXPIRE,

}
