package io.github.architers.context.lock.eums;

import lombok.Getter;

/**
 * 使用的锁（用什么作为锁）
 *
 * @author luyi
 */
@Getter
public enum LockType {
    /**
     * 默认的锁
     * <li>使用使用该，会使用配置</li>
     */
    DEFAULT("default", "默认的配置"),
    /**
     * jdk的本地锁
     */
    JDK("jdk", "jdk当前jvm锁"),
    /**
     * redis
     */
    REDISSION("redission", "redission分布式锁"),
    /**
     * zookeeper
     */
    ZK("zookeeper", "zookeeper分布式锁");

    private final String type;

    private final String caption;


    LockType(String type, String caption) {
        this.type = type;
        this.caption = caption;
    }
}
