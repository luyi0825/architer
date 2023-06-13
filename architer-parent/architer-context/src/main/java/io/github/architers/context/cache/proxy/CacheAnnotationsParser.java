package io.github.architers.context.cache.proxy;


import io.github.architers.context.cache.annotation.*;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.*;

/**
 * @author luyi
 * 缓存注解解析,解析方法有哪些缓存操作的注解
 */
public class CacheAnnotationsParser {

    Map<AnnotatedElement, Collection<Annotation>> annotationCache = new HashMap<>(32);

    private static final Set<Class<? extends Annotation>> CACHE_OPERATION_ANNOTATIONS = new HashSet<>(6, 1);

    static {
        CACHE_OPERATION_ANNOTATIONS.add(Cacheable.class);
        CACHE_OPERATION_ANNOTATIONS.add(Caching.class);
        CACHE_OPERATION_ANNOTATIONS.add(CachingBatch.class);
        CACHE_OPERATION_ANNOTATIONS.add(CacheEvict.class);
        CACHE_OPERATION_ANNOTATIONS.add(CacheEvictAll.class);
        CACHE_OPERATION_ANNOTATIONS.add(CachePut.class);
        CACHE_OPERATION_ANNOTATIONS.add(CacheBatchEvict.class);
        CACHE_OPERATION_ANNOTATIONS.add(CacheBatchPut.class);
    }

    /**
     * 判断类是否能够被满足
     *
     * @param targetClass 目标类
     * @return true 表示这个类有缓存注解
     */
    public boolean isCandidateClass(Class<?> targetClass) {
        return true;
    }


    public Collection<? extends Annotation> parse(AnnotatedElement annotatedElement) {
        Collection<? extends Annotation> ops = annotationCache.get(annotatedElement);
        if (ops != null) {
            return ops;
        }
        ops = parseCacheAnnotations(annotatedElement, false);
        if (ops.size() > 1) {
            // More than one operation found -> local declarations override interface-declared ones...
            Collection<? extends Annotation> localOps = parseCacheAnnotations(annotatedElement, true);
            if (!CollectionUtils.isEmpty(localOps)) {
                ops = localOps;
            }
        }
        if (!CollectionUtils.isEmpty(ops)) {
            Collection<Annotation> annotations = new ArrayList<>(ops.size());
            annotations.addAll(ops);
            this.annotationCache.put(annotatedElement, annotations);
        }
        return ops;
    }

    /**
     * 解析缓存注解:最终解析成cacheable、putCache、deleteCache
     *
     * @param annotatedElement 注解的元素
     * @param localOnly        是否只包含本方法
     * @return 缓存操作集合信息
     */
    private Collection<? extends Annotation> parseCacheAnnotations(
            AnnotatedElement annotatedElement, boolean localOnly) {
        Collection<? extends Annotation> anns = (localOnly ?
                AnnotatedElementUtils.getAllMergedAnnotations(annotatedElement, CACHE_OPERATION_ANNOTATIONS) :
                AnnotatedElementUtils.findAllMergedAnnotations(annotatedElement, CACHE_OPERATION_ANNOTATIONS));
        final Collection<Annotation> ops = new ArrayList<>(anns.size());
        for (Annotation ann : anns) {
            if (ann instanceof Caching) {
                Caching caching = (Caching) ann;
                ops.addAll(List.of(caching.cacheables()));
                ops.addAll(List.of(caching.evicts()));
                ops.addAll(List.of(caching.puts()));
            } else if (ann instanceof CachingBatch) {
                CachingBatch cachingBatch = (CachingBatch) ann;
                ops.addAll(List.of(cachingBatch.batchEvicts()));
                ops.addAll(List.of(cachingBatch.batchPuts()));
            } else {
                ops.add(ann);
            }
        }
        return ops;
    }


}
