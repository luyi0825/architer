package com.lz.core.cache.common.operation;

import com.lz.core.cache.common.CacheManager;
import com.lz.core.cache.common.enums.LockType;
import com.lz.core.cache.common.key.KeyGenerator;
import com.lz.lock.LockService;
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
    @Autowired(required = false)
    protected CacheManager cacheManager;

    @Autowired(required = false)
    private KeyGenerator keyGenerator;
    @Autowired(required = false)
    private LockService lockService;

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
     * @param method    注解的方法
     * @param target    目标对象
     * @param operation 缓存注解对应实体解析
     * @param args      方法参数
     * @return 处理后的结果值，这个值就是缓存注解方法对应的返回值
     */
    public final Object handler(Object target, Method method, Object[] args, CacheOperation operation) {
        String key = keyGenerator.getKey(target, method, args, operation);
        Lock lock = this.getLock(key, operation.getLock());
        if (lock == null) {
            return executeCacheHandler(key, target, method, args, operation);
        }
        lock.lock();
        try {
            return executeCacheHandler(key, target, method, args, operation);
        } finally {
            lock.unlock();
        }
    }


    protected abstract Object executeCacheHandler(String key, Object target, Method method, Object[] args, CacheOperation operation);


    /**
     * 通过缓存注解得到对应的锁
     */
    protected Lock getLock(String key, LockType lockType) {
        if (lockType == LockType.none) {
            return null;
        }
        String lockName = key + ".lock";
        switch (lockType) {
            case read:
                return lockService.getReadLock(lockName);
            case write:
                return lockService.getWriteLock(lockName);
            case reentrant:
                return lockService.getReentrantLock(lockName);
            default:
                throw new IllegalArgumentException("lock not match");
        }
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
}
