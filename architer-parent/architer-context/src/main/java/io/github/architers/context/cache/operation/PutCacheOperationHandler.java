package io.github.architers.context.cache.operation;


import io.github.architers.context.cache.CacheConstants;
import io.github.architers.context.cache.CacheUtils;
import io.github.architers.context.cache.annotation.PutCache;
import io.github.architers.context.cache.proxy.MethodReturnValueFunction;
import io.github.architers.context.expression.ExpressionMetadata;
import org.springframework.security.core.parameters.P;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;

/**
 * 对应PutCacheOperation的handler,先调用返回结果，后put.
 * <li>默认使用方法的返回值作为缓存，如果指定了使用了缓存值就用指定的缓存值</li>
 * <li>支持批量设置缓存</li>
 * <li>支持锁</li>
 *
 * @author luyi
 * @version 1.0.0
 */
public class PutCacheOperationHandler extends CacheOperationHandler {

    private static final int SECOND_ORDER = 2;

    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof PutCache;
    }

    @Override
    protected void execute(Annotation operationAnnotation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {
        PutCache putCache = (PutCache) operationAnnotation;
        //获取调用的方法的返回值
        Object value = methodReturnValueFunction.proceed();
        if (!this.canDoCacheOperate(putCache.condition(), putCache.unless(), expressionMetadata)) {
            return;
        }
        //默认为方法的返回值，当设置了缓存值就用指定的缓存值
        if (StringUtils.hasText(putCache.cacheValue())) {
            value = expressionParser.parserExpression(expressionMetadata, putCache.cacheValue());
        }
        long expireTime = CacheUtils.getExpireTime(putCache.expireTime(),
                putCache.randomTime());
        Object finalValue = value;

        Object key = parseCacheKey(expressionMetadata, putCache.key());
        KeyGenerator keyGenerator = super.keyGeneratorFactory.getKeyGenerator(putCache.keyGenerator());
        String cacheKey = keyGenerator.generator(expressionMetadata, putCache.cacheValue(), key);
        CacheOperate cacheOperate = super.cacheCacheOperateFactory.getCacheOperate(putCache.cacheOperate());
        PutCacheParam putCacheParam = new PutCacheParam();
        putCacheParam.setCacheKey(cacheKey);
        putCacheParam.setCacheOperate(cacheOperate);
        putCacheParam.setCacheName(putCacheParam.getCacheName());
        putCacheParam.setCacheValue(value);
        putCacheParam.setExpireTime(expireTime);
        putCacheParam.setTimeUnit(putCacheParam.getTimeUnit());
        cacheOperate.put(putCacheParam);

    }

}
