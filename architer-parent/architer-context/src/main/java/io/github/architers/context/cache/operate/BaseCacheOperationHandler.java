package io.github.architers.context.cache.operate;


import io.github.architers.context.cache.*;
import io.github.architers.context.cache.proxy.MethodReturnValueFunction;
import io.github.architers.context.expression.ExpressionMetadata;
import io.github.architers.context.expression.ExpressionParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.text.MessageFormat;
import java.util.List;


/**
 * @author luyi
 * 缓存operation处理基类
 * <li>对于实现类order排序,按照操作的频率排好序，增加程序效率：比如缓存读多，就把cacheable对应的处理器放最前边</li>
 */
public abstract class BaseCacheOperationHandler {


    @Resource
    protected CacheOperateSupport cacheOperateSupport;


    @Autowired(required = false)
    protected ExpressionParser expressionParser;

    @Autowired(required = false)
    protected List<CacheOperateEndHook> cacheOperateEndHooks;


    public Object value(String valueExpression, ExpressionMetadata expressionMetadata) {
        return expressionParser.parserExpression(expressionMetadata, valueExpression);
    }


    /**
     * operation是否匹配
     *
     * @param operationAnnotation operation对应的操作缓存的注解
     * @return 是否匹配，如果true就对这个operation的进行缓存处理
     */
    public abstract boolean match(Annotation operationAnnotation);

    protected Object parseCacheKey(ExpressionMetadata expressionMetadata, String key) {
        if (!StringUtils.hasText(key)) {
            throw new IllegalArgumentException("key is empty");
        }
        if (CacheConstants.BATCH_CACHE_KEY.equals(key)) {
            return key;
        }
        Object realKey = expressionParser.parserExpression(expressionMetadata, key);
        if (realKey == null) {
            throw new RuntimeException("cacheKey 为空");
        }
        return realKey;
    }

    /**
     * 处理缓存operation
     *
     * @param methodReturnValueFunction
     * @param expressionMetadata
     * @throws Throwable
     */
    public void handler(Annotation operationAnnotation,
                        MethodReturnValueFunction methodReturnValueFunction,
                        ExpressionMetadata expressionMetadata) throws Throwable {
        this.executeCacheOperate(operationAnnotation, expressionMetadata, methodReturnValueFunction);

    }


    /**
     * 是否能够执行缓存操作
     *
     * @return true表示可以进行缓存操作
     */
    public boolean canDoCacheOperate(String condition, String unless, ExpressionMetadata expressionMetadata) {
        return isCondition(condition, expressionMetadata) && !isUnless(unless, expressionMetadata);
    }

    /**
     * condition是否为true
     */
    public boolean isCondition(String condition, ExpressionMetadata expressionMetadata) {
        //条件满足才缓存
        if (StringUtils.hasText(condition)) {
            Object isCondition = expressionParser.parserExpression(expressionMetadata, condition);
            if (!(isCondition instanceof Boolean)) {
                throw new IllegalArgumentException(MessageFormat.format("condition[{0}]有误,必须为Boolean类型", condition));
            }
            return (boolean) isCondition;
        }
        return true;
    }

    protected String getWrapperCacheName(String originCacheName, ExpressionMetadata expressionMetadata) {

        CacheNameWrapper cacheNameWrapper = cacheOperateSupport.getCacheNameWrapper(originCacheName);
        if (cacheNameWrapper == null) {
            return originCacheName;
        }
        return cacheNameWrapper.getCacheName(expressionMetadata, originCacheName);
    }


    /**
     * 是否unless
     * 返回为true说明就不能进行缓存操作
     */
    public boolean isUnless(String unless, ExpressionMetadata expressionMetadata) {
        if (StringUtils.hasText(unless)) {
            Object isUnless = expressionParser.parserExpression(expressionMetadata, unless);
            if (!(isUnless instanceof Boolean)) {
                throw new IllegalArgumentException(MessageFormat.format("unless[{0}]有误,必须为Boolean类型", unless));
            }
            return (boolean) isUnless;
        }
        return false;
    }

    /**
     * 执行缓存处理
     *
     * @param operationAnnotation       缓存注解
     * @param expressionMetadata        表达式元数据
     * @param methodReturnValueFunction 返回值功能函数
     * @throws Throwable
     */
    protected abstract void executeCacheOperate(Annotation operationAnnotation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable;


    public BaseCacheOperationHandler setExpressionParser(ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
        return this;
    }


}
