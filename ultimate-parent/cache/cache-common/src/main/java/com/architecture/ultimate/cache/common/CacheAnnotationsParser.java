package com.architecture.ultimate.cache.common;


import com.architecture.ultimate.cache.common.annotation.Cacheable;
import com.architecture.ultimate.cache.common.annotation.Caching;
import com.architecture.ultimate.cache.common.annotation.DeleteCache;
import com.architecture.ultimate.cache.common.annotation.PutCache;
import com.architecture.ultimate.cache.common.operation.*;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.*;

/**
 * @author luyi
 * 缓存注解解析,解析方法有哪些缓存操作的注解
 */
public class CacheAnnotationsParser {

    private static final Set<Class<? extends Annotation>> CACHE_OPERATION_ANNOTATIONS = new LinkedHashSet<>(8);

    static {
        CACHE_OPERATION_ANNOTATIONS.add(Cacheable.class);
        CACHE_OPERATION_ANNOTATIONS.add(DeleteCache.class);
        CACHE_OPERATION_ANNOTATIONS.add(PutCache.class);
    }


    public boolean isCandidateClass(Class<?> targetClass) {
        return AnnotationUtils.isCandidateClass(targetClass, CACHE_OPERATION_ANNOTATIONS);
    }


    public Collection<CacheOperation> parse(AnnotatedElement annotatedElement) {
        Collection<CacheOperation> ops = parseCacheAnnotations(annotatedElement, false);
        if (ops != null && ops.size() > 1) {
            // More than one operation found -> local declarations override interface-declared ones...
            Collection<CacheOperation> localOps = parseCacheAnnotations(annotatedElement, true);
            if (localOps != null) {
                return localOps;
            }
        }
        return ops;
    }

    /**
     * 解析缓存注解
     *
     * @param annotatedElement 注解的元素
     * @param localOnly        是否只包含本方法
     * @return 缓存操作集合信息
     */
    @Nullable
    private Collection<CacheOperation> parseCacheAnnotations(
            AnnotatedElement annotatedElement, boolean localOnly) {
        Collection<? extends Annotation> anns = (localOnly ?
                AnnotatedElementUtils.getAllMergedAnnotations(annotatedElement, CACHE_OPERATION_ANNOTATIONS) :
                AnnotatedElementUtils.findAllMergedAnnotations(annotatedElement, CACHE_OPERATION_ANNOTATIONS));
        if (anns.isEmpty()) {
            return null;
        }
        final Collection<CacheOperation> ops = new ArrayList<>(anns.size());
        anns.forEach(annotation -> {
            CacheOperation cacheOperation = null;
            if (annotation instanceof PutCache) {
                cacheOperation = parsePutCacheAnnotation(annotatedElement, (PutCache) annotation);
            } else if (annotation instanceof DeleteCache) {
                cacheOperation = parseDeleteCacheAnnotation(annotatedElement, (DeleteCache) annotation);
            } else if (annotation instanceof Cacheable) {
                cacheOperation = parseCacheableAnnotation(annotatedElement, (Cacheable) annotation);
            } else if (annotation instanceof Caching) {
                cacheOperation = parseCachingAnnotation(annotatedElement, (Caching) annotation);
            }
            if (cacheOperation != null) {
                ops.add(cacheOperation);
            }
        });
        return ops;
    }

    /**
     * 解析@Caching注解
     */
    private CachingOperation parseCachingAnnotation(AnnotatedElement annotatedElement, Caching caching) {
        CachingOperation cachingOperation = new CachingOperation();
        List<CacheOperation> cacheOperationList = new ArrayList<>(4);
        Cacheable[] cacheables = caching.cacheable();
        if (ArrayUtils.isNotEmpty(cacheables)) {
            for (Cacheable cacheable : cacheables) {
                CacheableOperation cacheableOperation = this.parseCacheableAnnotation(annotatedElement, cacheable);
                cacheOperationList.add(cacheableOperation);
            }
        }
        PutCache[] putCaches = caching.put();
        if (ArrayUtils.isNotEmpty(putCaches)) {
            for (PutCache putCache : putCaches) {
                CacheOperation putOperation = this.parsePutCacheAnnotation(annotatedElement, putCache);
                cacheOperationList.add(putOperation);
            }
        }

        DeleteCache[] deleteCaches = caching.delete();
        if (ArrayUtils.isNotEmpty(deleteCaches)) {
            for (DeleteCache deleteCache : deleteCaches) {
                CacheOperation deleteOperation = this.parseDeleteCacheAnnotation(annotatedElement, deleteCache);
                cacheOperationList.add(deleteOperation);
            }
        }
        cachingOperation.setOperations(cacheOperationList.toArray(new CacheOperation[0]));
        return cachingOperation;
    }

    /**
     * 解析@PutCache注解
     */
    private CacheOperation parsePutCacheAnnotation(AnnotatedElement annotatedElement, PutCache cachePut) {
        PutCacheOperation putCacheOperation = new PutCacheOperation();
        putCacheOperation.setName(annotatedElement.toString());
        putCacheOperation.setKey(cachePut.key());
        putCacheOperation.setCacheName(cachePut.cacheName());
        putCacheOperation.setLock(cachePut.lock());
        putCacheOperation.setAsync(cachePut.async());
        putCacheOperation.setExpireTime(cachePut.expireTime());
        putCacheOperation.setRandomExpireTime(cachePut.randomExpireTime());
        putCacheOperation.setAnnotation(cachePut);
        putCacheOperation.setCacheValue(cachePut.cacheValue());
        return putCacheOperation;
    }

    /**
     * 解析删除缓存注解
     */
    private CacheOperation parseDeleteCacheAnnotation(AnnotatedElement annotatedElement, DeleteCache deleteCache) {
        DeleteCacheOperation deleteCacheOperation = new DeleteCacheOperation();
        deleteCacheOperation.setName(annotatedElement.toString());
        deleteCacheOperation.setCacheName(deleteCache.cacheName());
        deleteCacheOperation.setLock(deleteCache.lock());
        deleteCacheOperation.setAsync(deleteCache.async());
        deleteCacheOperation.setKey(deleteCache.key());
        deleteCacheOperation.setAnnotation(deleteCache);
        return deleteCacheOperation;
    }

    /**
     * 解析@Cacheable解析
     */
    private CacheableOperation parseCacheableAnnotation(AnnotatedElement annotatedElement, Cacheable cacheable) {
        CacheableOperation operation = new CacheableOperation();
        operation.setName(annotatedElement.toString());
        operation.setCacheName(cacheable.cacheName());
        operation.setLock(cacheable.lock());
        operation.setAsync(cacheable.async());
        operation.setKey(cacheable.key());
        operation.setExpireTime(cacheable.expireTime());
        operation.setRandomExpireTime(cacheable.randomExpireTime());
        operation.setAnnotation(cacheable);
        operation.setCacheValue(cacheable.cacheValue());
        return operation;
    }
}
