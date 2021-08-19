package com.architecture.context.common.lock;

/**
 * @author 锁的类型
 */
public enum LockType {
    /**
     * jdk的本地锁
     */
    JDK("lock_jdk"),
    /**
     * redis
     */
    REDIS("lock_redis"),
    /**
     * zookeeper
     */
    ZK("lock_zk");
    private final String beanName;

    LockType(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

    @Override
    public String toString() {
        return "LockType{" +
                "beanName='" + beanName + '\'' +
                '}';
    }
}
