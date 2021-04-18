package com.lz.core.cache.common;

import com.lz.core.cache.common.annotation.Cacheable;
import com.lz.core.cache.common.annotation.DeleteCache;
import com.lz.core.cache.common.annotation.PutCache;
import com.lz.core.cache.common.enums.LockType;
import com.lz.core.cache.common.key.KeyGenerator;
import com.lz.lock.distributed.LockService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.locks.Lock;

/**
 * @author luyi
 * 缓存处理抽象类
 */
public class DefaultCacheProcess implements CacheProcess {

    private KeyGenerator keyGenerator;
    private AnnotationCacheOperation cacheOperation;
    private LockService lockService;

    @Override
    public Object process(Object target, Method method, Object[] args, Class annotationClass) throws NoSuchFieldException, IllegalAccessException {
        String key = keyGenerator.getKey(target, method, args, annotationClass);
        Annotation annotation = method.getAnnotation(annotationClass);
        Lock lock = this.getLock(annotation, key);
        if (lock == null) {
            return cacheOperate(key, target, method, args, annotation);
        }
        lock.lock();
        try {
            return cacheOperate(key, target, method, args, annotation);
        } finally {
            lock.unlock();
        }
    }

    protected Object cacheOperate(String key, Object target, Method method, Object[] args, Annotation annotation) {
        Object value;
        if (annotation instanceof DeleteCache) {
            value = invoke(target, method, args);
            cacheOperation.deleteCache(key);
        } else if (annotation instanceof PutCache) {
            value = invoke(target, method, args);
            cacheOperation.putCache(key, value);
        } else if (annotation instanceof Cacheable) {
            value = cacheOperation.getCache(key);
            if (value == null) {
                value = cacheOperation.putCache(key, invoke(target, method, args));
            }
        } else {
            throw new IllegalArgumentException("annotationClass not match");
        }
        return value;
    }

    /**
     * 得到锁
     */
    protected Lock getLock(Annotation annotation, String key) throws NoSuchFieldException, IllegalAccessException {
        if (true) {
            return null;
        }
        //@TODO 获取注解的属性值
        Field[] fields = annotation.annotationType().getClass().getFields();
        annotation.annotationType().getFields();
        Field field = annotation.getClass().getField("lock");
        System.out.println(annotation.getClass().getFields());

        LockType lockType = (LockType) field.get(annotation);
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

    public Object invoke(Object target, Method method, Object[] args) {
        try {
            return method.invoke(target, args);
        } catch (Exception e) {
            throw new RuntimeException("操作失败", e);
        }
    }

    @Autowired
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Autowired
    public void setCacheOperation(AnnotationCacheOperation cacheOperation) {
        this.cacheOperation = cacheOperation;
    }

    @Autowired(required = false)
    public void setLockService(LockService lockService) {
        this.lockService = lockService;
    }
}
