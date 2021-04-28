package com.lz.core.cache.common;


import com.lz.core.cache.common.annotation.DeleteCache;
import com.lz.core.cache.common.annotation.PutCache;
import com.lz.core.cache.common.annotation.Cacheable;
import com.lz.core.cache.common.operation.CacheOperation;
import com.lz.core.cache.common.operation.CacheableOperation;
import com.lz.core.cache.common.operation.DeleteCacheOperation;
import com.lz.core.cache.common.operation.PutCacheOperation;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

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
            }
            if (cacheOperation != null) {
                ops.add(cacheOperation);
            }
        });
        return ops;
    }

    /**
     * 解析@PutCache注解
     */
    private CacheOperation parsePutCacheAnnotation(AnnotatedElement annotatedElement, PutCache cachePut) {
        PutCacheOperation putCacheOperation = new PutCacheOperation();
        putCacheOperation.setName(annotatedElement.toString());
        putCacheOperation.setPrefix(cachePut.prefix());
        putCacheOperation.setCacheName(cachePut.cacheName());
        putCacheOperation.setSuffix(cachePut.suffix());
        putCacheOperation.setLock(cachePut.lock());
        putCacheOperation.setAsync(cachePut.async());
        putCacheOperation.setExpireTime(cachePut.expireTime());
        putCacheOperation.setRandomExpireTime(cachePut.randomExpireTime());
        putCacheOperation.setAnnotation(cachePut);
        putCacheOperation.setCacheName(putCacheOperation.getCacheValue());
        return putCacheOperation;
    }

    /**
     * 解析删除缓存注解
     */
    private CacheOperation parseDeleteCacheAnnotation(AnnotatedElement annotatedElement, DeleteCache deleteCache) {
        DeleteCacheOperation deleteCacheOperation = new DeleteCacheOperation();
        deleteCacheOperation.setName(annotatedElement.toString());
        deleteCacheOperation.setPrefix(deleteCache.prefix());
        deleteCacheOperation.setCacheName(deleteCache.cacheName());
        deleteCacheOperation.setLock(deleteCache.lock());
        deleteCacheOperation.setAsync(deleteCache.async());
        deleteCacheOperation.setSuffix(deleteCache.suffix());
        deleteCacheOperation.setAnnotation(deleteCache);
        return deleteCacheOperation;
    }

    /**
     * 解析@Cacheable解析
     */
    private CacheableOperation parseCacheableAnnotation(AnnotatedElement annotatedElement, Cacheable cacheable) {
        CacheableOperation operation = new CacheableOperation();
        operation.setName(annotatedElement.toString());
        operation.setPrefix(cacheable.prefix());
        operation.setCacheName(cacheable.cacheName());
        operation.setLock(cacheable.lock());
        operation.setAsync(cacheable.async());
        operation.setSuffix(cacheable.suffix());
        operation.setExpireTime(cacheable.expireTime());
        operation.setRandomExpireTime(cacheable.randomExpireTime());
        operation.setAnnotation(cacheable);
        operation.setCacheValue(cacheable.cacheValue());
        return operation;
    }
}
