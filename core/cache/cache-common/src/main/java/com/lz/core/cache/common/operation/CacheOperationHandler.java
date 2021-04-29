package com.lz.core.cache.common.operation;


import com.lz.cache.lock.LockManager;
import com.lz.core.cache.common.CacheExpressionParser;
import com.lz.core.cache.common.CacheManager;
import com.lz.core.cache.common.enums.LockType;
import com.lz.core.cache.common.key.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.locks.Lock;

/**
 * @author luyi
 * 缓存operation
 */
public abstract class CacheOperationHandler {

    /**
     * 缓存manager,定义protected，让实现类也可以直接使用
     */

    protected CacheManager cacheManager;

    private KeyGenerator keyGenerator;

    private LockManager lockManager;

    protected CacheExpressionParser cacheExpressionParser;

    public CacheOperationHandler() {

    }

    /**
     * operation是否匹配
     *
     * @param operationAnnotation operation对应的缓存注解
     * @return 是否匹配，如果true就对这个operation的进行缓存处理
     */
    public abstract boolean match(Annotation operationAnnotation);

    /**
     * 开始处理
     *
     * @return 处理后的结果值，这个值就是缓存注解方法对应的返回值
     */
    public final Object handler(CacheOperationMetadata metadata) {
        String key = keyGenerator.getKey(metadata).toString();
        Lock lock = this.getLock(key, metadata.getCacheOperation().getLock());
        if (lock == null) {
            return executeCacheHandler(key, metadata);
        }
        lock.lock();
        try {
            return executeCacheHandler(key, metadata);
        } finally {
            lock.unlock();
        }
    }

    public void setCacheValue() {

    }

    /**
     * 执行缓存处理器
     *
     * @param key      缓存的key
     * @param metadata 缓存操作元数据
     */
    protected abstract Object executeCacheHandler(String key, CacheOperationMetadata metadata);


    /**
     * 通过缓存注解得到对应的锁
     */
    protected Lock getLock(String key, LockType lockType) {
        if (lockType == LockType.none) {
            return null;
        }
        String lockName = "lock." + key;
        switch (lockType) {
            case read:
                return lockManager.getReadLock(lockName);
            case write:
                return lockManager.getWriteLock(lockName);
            case reentrant:
                return lockManager.getReentrantLock(lockName);
            default:
                throw new IllegalArgumentException("lock not match");
        }
    }

    public Object invoke(CacheOperationMetadata metadata) {
        return invoke(metadata.getTarget(), metadata.getTargetMethod(), metadata.getArgs());
    }

    /**
     * 反射invoke,得到值
     */
    public Object invoke(Object target, Method method, Object[] args) {
        try {
            return method.invoke(target, args);
        } catch (Exception e) {
            throw new RuntimeException("操作失败", e);
        }
    }

    @Autowired
    public CacheOperationHandler setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        return this;
    }

    @Autowired(required = false)
    public CacheOperationHandler setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
        return this;
    }

    @Autowired(required = false)
    public CacheOperationHandler setLockManager(LockManager lockManager) {
        this.lockManager = lockManager;
        return this;
    }

    @Autowired
    public void setCacheExpressionParser(CacheExpressionParser cacheExpressionParser) {
        this.cacheExpressionParser = cacheExpressionParser;
    }
}
