package com.lz.core.cache.common;

import com.lz.core.cache.common.annotation.Cacheable;
import com.lz.core.cache.common.annotation.DeleteCache;
import com.lz.core.cache.common.annotation.PutCache;
import com.lz.core.cache.common.enums.LockType;
import com.lz.core.cache.common.key.KeyGenerator;
import com.lz.lock.distributed.LockService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.concurrent.locks.Lock;

/**
 * @author luyi
 * 缓存处理抽象类
 * @TODO 当一个类存在多个注解怎么处理
 */
public class DefaultCacheProcess implements CacheProcess {

    private KeyGenerator keyGenerator;
    private CacheOperationService operationSource;
    private LockService lockService;


    @Override
    public Object process(Object target, Method method, Object[] args, Collection<CacheOperation> cacheOperations) {
        try {
            for (CacheOperation operation : cacheOperations) {
                String key = keyGenerator.getKey(target, method, args, operation);
                Lock lock = this.getLock(key, operation.getLock());
                if (lock == null) {
                    return cacheOperate(key, target, method, args, operation);
                }
                lock.lock();
                try {
                    return cacheOperate(key, target, method, args, operation);
                } finally {
                    lock.unlock();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("cache process error", e);
        }
        return null;
    }

    /**
     * 缓存操作
     *
     * @return method方法的返回值
     */
    protected Object cacheOperate(String key, Object target, Method method, Object[] args, CacheOperation operation) {
        Object value;
        Annotation annotation = operation.getAnnotation();
        if (annotation instanceof DeleteCache) {
            value = invoke(target, method, args);
            operationSource.deleteCache(key);
        } else if (annotation instanceof PutCache) {
            value = invoke(target, method, args);
            operationSource.putCache(key, value);
        } else if (annotation instanceof Cacheable) {
            value = operationSource.getCache(key);
            if (value == null) {
                value = operationSource.putCache(key, invoke(target, method, args));
            }
        } else {
            throw new IllegalArgumentException("annotationClass not match");
        }
        return value;
    }

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

    @Autowired(required = false)
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Autowired
    public void setCacheOperation(CacheOperationService operationSource) {
        this.operationSource = operationSource;
    }

    @Autowired(required = false)
    public void setLockService(LockService lockService) {
        this.lockService = lockService;
    }
}
