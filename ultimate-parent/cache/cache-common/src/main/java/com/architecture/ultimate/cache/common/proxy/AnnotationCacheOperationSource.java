package com.architecture.ultimate.cache.common.proxy;

import com.architecture.ultimate.cache.common.operation.CacheOperation;
import com.architecture.ultimate.cache.common.CacheAnnotationsParser;

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
    public Collection<CacheOperation> getCacheOperations(Method method, Class<?> targetClass) {
        return cacheAnnotationsParser.parse(method);
    }
}
