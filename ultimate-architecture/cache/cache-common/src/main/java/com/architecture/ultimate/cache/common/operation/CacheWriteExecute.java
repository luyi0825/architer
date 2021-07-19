package com.architecture.ultimate.cache.common.operation;

/**
 * 缓存写的执行器（包括put,delete）
 *
 * @author luyi
 */
public interface CacheWriteExecute {

    /**
     * 写操作
     */
    void write();

}
