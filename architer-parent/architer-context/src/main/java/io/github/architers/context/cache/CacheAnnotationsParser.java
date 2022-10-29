package io.github.architers.context.cache;


import io.github.architers.context.cache.annotation.*;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
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
        CACHE_OPERATION_ANNOTATIONS.add(Cacheables.class);
        CACHE_OPERATION_ANNOTATIONS.add(DeleteCache.class);
        CACHE_OPERATION_ANNOTATIONS.add(DeleteCaches.class);
        CACHE_OPERATION_ANNOTATIONS.add(PutCache.class);
        CACHE_OPERATION_ANNOTATIONS.add(PutCaches.class);
        CACHE_OPERATION_ANNOTATIONS.add(BatchDeleteCache.class);
        CACHE_OPERATION_ANNOTATIONS.add(BatchPutCache.class);
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
            Collection<Annotation> temp = new ArrayList<>(ops.size());
            //排序：主要让Cacheable先执行，其他的缓存操作可能需要他的结果
            for (Annotation op : ops) {
                if (op instanceof Cacheable) {
                    annotations.add(op);
                } else {
                    temp.add(op);
                }
            }
            //最后将非Cacheable放后端
            annotations.addAll(temp);
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
        anns.forEach(annotation -> {
            if (annotation instanceof Cacheables) {
                ops.addAll(Arrays.asList(((Cacheables) annotation).value()));
            } else if (annotation instanceof PutCaches) {
                ops.addAll(Arrays.asList(((PutCaches) annotation).value()));
            } else if (annotation instanceof DeleteCaches) {
                ops.addAll(Arrays.asList(((DeleteCaches) annotation).value()));
            } else {
                ops.add(annotation);
            }
        });
        return ops;
    }


}
