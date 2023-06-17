package io.github.architers.context.cache.operate;


import io.github.architers.context.cache.utils.CacheUtils;
import io.github.architers.context.cache.annotation.CachePut;
import io.github.architers.context.cache.model.PutParam;
import io.github.architers.context.cache.proxy.MethodReturnValueFunction;
import io.github.architers.context.expression.ExpressionMetadata;
import io.github.architers.context.utils.JsonUtils;

import java.lang.annotation.Annotation;

/**
 * 单个缓存put操作
 *
 * @author luyi
 * @version 1.0.0
 */
public class CachePutOperationHandler extends BaseCacheOperationHandler {

    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof CachePut;
    }

    @Override
    protected void executeCacheOperate(Annotation operationAnnotation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {
        CachePut cachePut = (CachePut) operationAnnotation;

        if (this.canDoCacheOperate(cachePut.condition(), cachePut.unless(), expressionMetadata)) {
            //调用方法
            return;
        }
        CacheOperateContext cacheOperateContext = super.cacheOperateSupport.getCacheOperateContext(cachePut.cacheName());

        //得到过期时间
        long expireTime = CacheUtils.getExpireTime(cachePut.expireTime(), cachePut.randomTime());

        Object key = parseCacheKey(expressionMetadata, cachePut.key());
        PutParam putParam = new PutParam();
        putParam.setWrapperCacheName(super.getWrapperCacheName(cacheOperateContext, expressionMetadata, cachePut.cacheName()));
        putParam.setOriginCacheName(cachePut.cacheName());
        putParam.setKey(JsonUtils.toJsonString(key));

        putParam.setExpireTime(expireTime);
        putParam.setTimeUnit(cachePut.timeUnit());

        if (cachePut.beforeInvocation()) {
            if (!super.beforeInvocation(putParam, cacheOperateContext)) {
                //说明中断执行
                return;
            }
        }

        //解析缓存值
        Object cacheValue = expressionParser.parserExpression(expressionMetadata, cachePut.cacheValue());
        putParam.setCacheValue(cacheValue);
        //执行缓存put操作
        cacheOperateContext.getCacheOperate().put(putParam);
        if (!cachePut.beforeInvocation()) {
            //调用执行后的钩子函数
            super.afterInvocation(putParam, cacheOperateContext);
        }
    }

}
