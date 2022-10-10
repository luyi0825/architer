package io.github.architers.context.cache;


import io.github.architers.context.cache.annotation.*;
import io.github.architers.context.cache.operation.*;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author luyi
 * 缓存注解解析,解析方法有哪些缓存操作的注解
 */
public class CacheAnnotationsParser {

    Map<AnnotatedElement, Collection<CacheOperation>> operationCache = new HashMap<>(32);


    private static final Set<Class<? extends Annotation>> CACHE_OPERATION_ANNOTATIONS = new HashSet<>(6, 1);

    static {
        CACHE_OPERATION_ANNOTATIONS.add(Cacheable.class);
        CACHE_OPERATION_ANNOTATIONS.add(Cacheables.class);
        CACHE_OPERATION_ANNOTATIONS.add(DeleteCache.class);
        CACHE_OPERATION_ANNOTATIONS.add(DeleteCaches.class);
        CACHE_OPERATION_ANNOTATIONS.add(PutCache.class);
        CACHE_OPERATION_ANNOTATIONS.add(PutCaches.class);
    }

    /**
     * 判断类是否能够被满足
     *
     * @param targetClass 目标类
     * @return true 表示这个类有缓存注解
     */
    public boolean isCandidateClass(Class<?> targetClass) {
        return AnnotationUtils.isCandidateClass(targetClass, CACHE_OPERATION_ANNOTATIONS);
    }


    public Collection<CacheOperation> parse(AnnotatedElement annotatedElement) {
        Collection<CacheOperation> ops = operationCache.get(annotatedElement);
        if (ops != null) {
            return ops;
        }
        ops = parseCacheAnnotations(annotatedElement, false);
        if (ops != null && ops.size() > 1) {
            // More than one operation found -> local declarations override interface-declared ones...
            Collection<CacheOperation> localOps = parseCacheAnnotations(annotatedElement, true);
            if (localOps != null) {
                ops = localOps;
            }
        }
        if (!CollectionUtils.isEmpty(ops)) {
            //排序：主要让Cacheable先执行，其他的缓存操作可能需要他的结果
            ops = ops.stream().sorted(Comparator.comparing(CacheOperation::getOrder)).collect(Collectors.toList());
            operationCache.put(annotatedElement, ops);
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
            if (annotation instanceof Cacheable) {
                parseCacheableAnnotation((Cacheable) annotation, ops);
            } else if (annotation instanceof Cacheables) {
                parseCacheablesAnnotation((Cacheables) annotation, ops);
            } else if (annotation instanceof PutCache) {
                parsePutCacheAnnotation((PutCache) annotation, ops);
            } else if (annotation instanceof PutCaches) {
                parsePutCachesAnnotation((PutCaches) annotation, ops);
            } else if (annotation instanceof DeleteCache) {
                parseDeleteCacheAnnotation((DeleteCache) annotation, ops);
            } else if (annotation instanceof DeleteCaches) {
                parseDeletesCacheAnnotation((DeleteCaches) annotation, ops);
            }
        });
        return ops;
    }

    private void parseCacheablesAnnotation(Cacheables cacheables, Collection<CacheOperation> ops) {
        Cacheable[] ables = cacheables.value();
        if (ables != null) {
            for (Cacheable cacheable : ables) {
                parseCacheableAnnotation(cacheable, ops);
            }
        }
    }

    private void parseDeletesCacheAnnotation(DeleteCaches deleteCaches, Collection<CacheOperation> ops) {
        DeleteCache[] deletes = deleteCaches.value();
        if (deletes != null) {
            for (DeleteCache deleteCache : deletes) {
                parseDeleteCacheAnnotation(deleteCache, ops);
            }
        }
    }

    /**
     * 解析PutCaches
     *
     * @param putCaches 批量防止设置缓存注解
     * @param ops       解析操作PutCacheOperation后放置的集合
     */
    private void parsePutCachesAnnotation(PutCaches putCaches, Collection<CacheOperation> ops) {
        PutCache[] puts = putCaches.value();
        if (puts != null) {
            for (PutCache put : puts) {
                parsePutCacheAnnotation(put, ops);
            }
        }
    }

    /**
     * 解析@PutCache注解
     */
    private void parsePutCacheAnnotation(PutCache cachePut,
                                         Collection<CacheOperation> ops) {
        PutCacheOperation putCacheOperation = new PutCacheOperation();
        putCacheOperation.setKey(cachePut.key());
        putCacheOperation.setCacheName(cachePut.cacheName());
        putCacheOperation.setAsync(cachePut.async());
        putCacheOperation.setExpireTime(cachePut.expireTime());
        putCacheOperation.setTimeUnit(cachePut.timeUnit());
        putCacheOperation.setRandomTime(cachePut.randomTime());
        putCacheOperation.setCondition(cachePut.condition());
        putCacheOperation.setUnless(cachePut.unless());
        putCacheOperation.setCacheValue(cachePut.cacheValue());
        putCacheOperation.setCacheMode(cachePut.cacheMode());
        ops.add(putCacheOperation);
    }

    /**
     * 解析删除缓存注解
     */
    private void parseDeleteCacheAnnotation(DeleteCache deleteCache,
                                            Collection<CacheOperation> ops) {
        DeleteCacheOperation deleteCacheOperation = new DeleteCacheOperation();
        deleteCacheOperation.setCacheName(deleteCache.cacheName());
        deleteCacheOperation.setKey(deleteCache.key());
        deleteCacheOperation.setAsync(deleteCache.async());
        deleteCacheOperation.setCacheValue(deleteCache.cacheValue());
        deleteCacheOperation.setCacheMode(deleteCache.cacheMode());
        ops.add(deleteCacheOperation);
    }

    /**
     * 解析@Cacheable解析
     */
    private void parseCacheableAnnotation(Cacheable cacheable, Collection<CacheOperation> ops) {
        CacheableCacheOperation operation = new CacheableCacheOperation();
        operation.setCacheName(cacheable.cacheName());
        operation.setExpireTime(cacheable.expireTime());
        operation.setTimeUnit(cacheable.timeUnit());
        operation.setRandomTime(cacheable.randomTime());
        operation.setAsync(cacheable.async());
        operation.setKey(cacheable.key());
        operation.setCondition(cacheable.condition());
        operation.setUnless(cacheable.unless());
        operation.setCacheMode(cacheable.cacheMode());
        //设置成最小，让cacheable最先执行
        operation.setOrder(-1);
        ops.add(operation);
    }

}
