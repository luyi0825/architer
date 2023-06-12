package io.github.architers.context.cache.operate;

import lombok.Data;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 方法缓存注解内容
 *
 * @author luyi
 */
@Data
public class MethodCacheAnnotationContext {

    /**
     * 在调用方法之前的注解信息
     */
    private Collection<Annotation> beforeInvocations;


    /**
     * cacheable注解信息
     */
    private Collection<Annotation> cacheables;

    /**
     * 调用方法后的注解信息
     */
    private Collection<Annotation> afterInvocations;


    public void addBeforeInvocations(Annotation annotation) {
        if (beforeInvocations == null) {
            beforeInvocations = new ArrayList<>(2);
        }
        beforeInvocations.add(annotation);
    }

    public void addCacheables(Annotation annotation) {
        if (cacheables == null) {
            cacheables = new ArrayList<>(2);
        }
        cacheables.add(annotation);
    }

    public void addAfterInvocations(Annotation annotation) {
        if (afterInvocations == null) {
            afterInvocations = new ArrayList<>(2);
        }
        afterInvocations.add(annotation);
    }

}
