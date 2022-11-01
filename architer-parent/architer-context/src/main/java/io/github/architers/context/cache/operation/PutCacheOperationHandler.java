package io.github.architers.context.cache.operation;


import io.github.architers.context.cache.CacheUtils;
import io.github.architers.context.cache.annotation.PutCache;
import io.github.architers.context.cache.proxy.MethodReturnValueFunction;
import io.github.architers.context.expression.ExpressionMetadata;
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


        Object key = parseCacheKey(expressionMetadata, putCache.key());
        KeyGenerator keyGenerator = super.keyGeneratorFactory.getKeyGenerator(putCache.keyGenerator());
        String cacheName = keyGenerator.generator(expressionMetadata, putCache.cacheName());
        CacheOperate cacheOperate = super.cacheOperateFactory.getCacheOperate(putCache.cacheOperate());
        PutParam putParam = new PutParam();
        putParam.setCacheName(cacheName);
        putParam.setKey(key);
        putParam.setCacheOperate(cacheOperate);
        putParam.setCacheName(putParam.getCacheName());
        putParam.setCacheValue(value);
        putParam.setExpireTime(expireTime);
        putParam.setTimeUnit(putParam.getTimeUnit());
        cacheOperate.put(putParam);

    }

}
