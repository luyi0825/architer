package io.github.architers.context.cache.operate;


import io.github.architers.context.cache.utils.CacheUtils;
import io.github.architers.context.cache.model.InvalidCacheValue;
import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.context.cache.model.GetParam;
import io.github.architers.context.cache.model.PutParam;
import io.github.architers.context.cache.proxy.MethodReturnValueFunction;
import io.github.architers.context.expression.ExpressionMetadata;
import io.github.architers.context.utils.JsonUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Annotation;


/**
 * Cacheable 注解 对应的处理类
 * 当缓存中没有值的时候，查询数据库，并将返回值放入缓存
 * <li>这个使用的频率最好，用HIGHEST_PRECEDENCE排在最前边</li>
 *
 * @author luyi
 * @version 1.0.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CacheableOperationHandler extends BaseCacheOperationHandler {


    @Override
    public boolean match(Annotation annotation) {
        return annotation instanceof Cacheable;
    }

    @Override
    protected void executeCacheOperate(Annotation operationAnnotation, ExpressionMetadata expressionMetadata,
                                       MethodReturnValueFunction methodReturnValueFunction) throws Throwable {
        Cacheable cacheable = (Cacheable) operationAnnotation;

        Object cacheValue = null;
        //判断是或能够进行缓存操作
        if (canDoCacheOperate(cacheable.condition(), cacheable.unless(), expressionMetadata)) {
            return;
        }
        CacheOperate cacheOperate = super.cacheOperateManager.getCacheOperate(cacheable.cacheName());

        Object key = super.parseCacheKey(expressionMetadata, cacheable.key());

        GetParam getParam = new GetParam();
        //同步：没有值才查询数据库
        getParam.setAsync(false);
        getParam.setOriginCacheName(cacheable.cacheName());
        getParam.setWrapperCacheName(getWrapperCacheName(cacheable.cacheName(), expressionMetadata));
        getParam.setKey(JsonUtils.toJsonString(key));
        Object value = cacheOperate.get(getParam);
        if (!isNullValue(value)) {
            cacheValue = value;
        }
        if (cacheValue == null) {

            //本地缓存多次put没关系，当时防止多次查询数据库
            synchronized (cacheable.cacheName().intern()) {
                //先判断缓存有没有，缓存有值就返回
                getParam.setAsync(false);
                getParam.setOriginCacheName(cacheable.cacheName());
                getParam.setWrapperCacheName(getWrapperCacheName(cacheable.cacheName(), expressionMetadata));
                getParam.setKey(JsonUtils.toJsonString(key));
                value = cacheOperate.get(getParam);

                if (value != null) {
                    methodReturnValueFunction.setValue(value);
                    return;
                }
                //没有值就再调用
                Object returnValue = methodReturnValueFunction.proceed();
                long expireTime = CacheUtils.getExpireTime(cacheable.expireTime(), cacheable.randomTime());
                PutParam putParam = new PutParam();
                putParam.setWrapperCacheName(getParam.getWrapperCacheName());
                putParam.setOriginCacheName(getParam.getOriginCacheName());
                putParam.setCacheValue(returnValue);
                putParam.setKey(JsonUtils.toJsonString(key));
                putParam.setTimeUnit(cacheable.timeUnit());
                putParam.setExpireTime(expireTime);
                cacheOperate.put(putParam);


            }

        } else {
            //设置返回值，防止方法出现其他注解调用methodReturnValueFunction.proceed()，造成重复调用
            methodReturnValueFunction.setValue(cacheValue);
        }
    }

    /**
     * 判断是否是空值
     *
     * @param value 缓存值
     * @return true表示为空值
     */
    private boolean isNullValue(Object value) {
        return value == null || value instanceof InvalidCacheValue;
    }

}
