package io.github.architers.context.lock.proxy;


import io.github.architers.context.lock.annotation.ExclusiveLock;
import io.github.architers.context.lock.annotation.ReadLock;
import io.github.architers.context.lock.annotation.WriteLock;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.*;

/**
 * @author luyi
 * 缓存注解解析,解析方法有哪些缓存操作的注解
 */
public class LockAnnotationsParser {

    Map<AnnotatedElement, Collection<Annotation>> annotationCache = new HashMap<>(32);

    private static final Set<Class<? extends Annotation>> LOCK_OPERATION_ANNOTATIONS = new HashSet<>(6, 1);

    static {
        LOCK_OPERATION_ANNOTATIONS.add(ExclusiveLock.class);
        LOCK_OPERATION_ANNOTATIONS.add(ReadLock.class);
        LOCK_OPERATION_ANNOTATIONS.add(WriteLock.class);
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
     *
     * @param annotatedElement 注解的元素
     * @param localOnly        是否只包含本方法
     * @return 锁的注解信息
     */
    private Collection<? extends Annotation> parseCacheAnnotations(
            AnnotatedElement annotatedElement, boolean localOnly) {
        Collection<? extends Annotation> lockAnnotations = (localOnly ?
                AnnotatedElementUtils.getAllMergedAnnotations(annotatedElement, LOCK_OPERATION_ANNOTATIONS) :
                AnnotatedElementUtils.findAllMergedAnnotations(annotatedElement, LOCK_OPERATION_ANNOTATIONS));
        final Collection<Annotation> ops = new ArrayList<>(lockAnnotations.size());
        ops.addAll(lockAnnotations);
        return ops;
    }


}
