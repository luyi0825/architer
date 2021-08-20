package com.architecture.context.cache;

import com.architecture.context.cache.annotation.DeleteCache;
import com.architecture.context.cache.annotation.PutCache;
import com.architecture.context.cache.operation.CacheOperation;
import com.architecture.context.cache.operation.CacheableOperation;
import com.architecture.context.cache.operation.DeleteCacheOperation;
import com.architecture.context.cache.operation.PutCacheOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import com.architecture.context.cache.annotation.Cacheable;
import com.architecture.context.cache.annotation.Caching;

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
        CACHE_OPERATION_ANNOTATIONS.add(Caching.class);
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
            if (annotation instanceof PutCache) {
                parsePutCacheAnnotation(annotatedElement, (PutCache) annotation, ops);
            } else if (annotation instanceof DeleteCache) {
                parseDeleteCacheAnnotation(annotatedElement, (DeleteCache) annotation, ops);
            } else if (annotation instanceof Cacheable) {
                parseCacheableAnnotation(annotatedElement, (Cacheable) annotation, ops);
            } else if (annotation instanceof Caching) {
                parseCachingAnnotation(annotatedElement, (Caching) annotation, ops);
            }
        });
        return ops;
    }

    /**
     * 解析@Caching注解
     */
    private void parseCachingAnnotation(AnnotatedElement annotatedElement, Caching caching, Collection<CacheOperation> ops) {
        Cacheable[] cacheables = caching.cacheable();
        if (ArrayUtils.isNotEmpty(cacheables)) {
            for (Cacheable cacheable : cacheables) {
                this.parseCacheableAnnotation(annotatedElement, cacheable, ops);

            }
        }
        PutCache[] putCaches = caching.put();
        if (ArrayUtils.isNotEmpty(putCaches)) {
            for (PutCache putCache : putCaches) {
                this.parsePutCacheAnnotation(annotatedElement, putCache, ops);
            }
        }

       DeleteCache[] deleteCaches = caching.delete();
        if (ArrayUtils.isNotEmpty(deleteCaches)) {
            for (DeleteCache deleteCache : deleteCaches) {
                this.parseDeleteCacheAnnotation(annotatedElement, deleteCache, ops);
            }
        }
    }

    /**
     * 解析@PutCache注解
     */
    private void parsePutCacheAnnotation(AnnotatedElement annotatedElement,
                                         PutCache cachePut,
                                         Collection<CacheOperation> ops) {
        PutCacheOperation putCacheOperation = new PutCacheOperation();
        putCacheOperation.setKey(cachePut.key());
        putCacheOperation.setCacheName(cachePut.cacheName());
        putCacheOperation.setLockType(cachePut.lockType());
        putCacheOperation.setAsync(cachePut.async());
        putCacheOperation.setExpireTime(cachePut.expireTime());
        putCacheOperation.setRandomExpireTime(cachePut.randomExpireTime());
        putCacheOperation.setAnnotation(cachePut);
        putCacheOperation.setCacheValue(cachePut.cacheValue());
        ops.add(putCacheOperation);
    }

    /**
     * 解析删除缓存注解
     */
    private void parseDeleteCacheAnnotation(AnnotatedElement annotatedElement,
                                            DeleteCache deleteCache,
                                            Collection<CacheOperation> ops) {
        DeleteCacheOperation deleteCacheOperation = new DeleteCacheOperation();
        deleteCacheOperation.setCacheName(deleteCache.cacheName());
        deleteCacheOperation.setLock(deleteCache.lock());
        deleteCacheOperation.setLockType(deleteCache.lockType());
        deleteCacheOperation.setAsync(deleteCache.async());
        deleteCacheOperation.setKey(deleteCache.key());
        deleteCacheOperation.setAnnotation(deleteCache);
        ops.add(deleteCacheOperation);
    }

    /**
     * 解析@Cacheable解析
     */
    private void parseCacheableAnnotation(AnnotatedElement annotatedElement, Cacheable cacheable, Collection<CacheOperation> ops) {
        CacheableOperation operation = new CacheableOperation();
        operation.setCacheName(cacheable.cacheName());
        operation.setLockType(cacheable.lockType());
        operation.setLock(cacheable.lock());
        operation.setAsync(cacheable.async());
        operation.setKey(cacheable.key());
        operation.setExpireTime(cacheable.expireTime());
        operation.setRandomExpireTime(cacheable.randomExpireTime());
        operation.setAnnotation(cacheable);
        operation.setCacheValue(cacheable.cacheValue());
        ops.add(operation);
    }
}
