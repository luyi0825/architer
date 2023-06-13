package io.github.architers.context.cache.proxy;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;


/**
 * @author luyi
 * 注解缓存操作元
 */
public class AnnotationCacheOperationSource implements CacheOperationSource {

    /**
     * 缓存解析器
     */
    private final CacheAnnotationsParser cacheAnnotationsParser;

    public AnnotationCacheOperationSource(CacheAnnotationsParser cacheAnnotationsParser) {
        this.cacheAnnotationsParser = cacheAnnotationsParser;
    }

    @Override
    public boolean isCandidateClass(Class<?> targetClass) {
        return cacheAnnotationsParser.isCandidateClass(targetClass);
    }

    @Override
    public Collection<?extends Annotation> getCacheOperations(Method method, Class<?> targetClass) {
        return cacheAnnotationsParser.parse(method);
    }
}
