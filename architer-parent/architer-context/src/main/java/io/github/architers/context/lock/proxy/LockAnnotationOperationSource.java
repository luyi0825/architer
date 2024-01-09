package io.github.architers.context.lock.proxy;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;


/**
 * @author luyi
 * 注解缓存操作元
 */
public class LockAnnotationOperationSource implements LockOperationSource {

    /**
     * 缓存解析器
     */
    private final LockAnnotationsParser lockAnnotationsParser;

    public LockAnnotationOperationSource(LockAnnotationsParser lockAnnotationsParser) {
        this.lockAnnotationsParser = lockAnnotationsParser;
    }

    @Override
    public boolean isCandidateClass(Class<?> targetClass) {
        return lockAnnotationsParser.isCandidateClass(targetClass);
    }

    @Override
    public Collection<? extends Annotation> getCacheOperations(Method method, Class<?> targetClass) {
        return lockAnnotationsParser.parse(method);
    }
}
