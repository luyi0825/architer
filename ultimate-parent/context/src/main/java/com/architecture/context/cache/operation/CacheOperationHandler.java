package com.architecture.context.cache.operation;


import com.architecture.context.cache.proxy.MethodReturnValueFunction;
import com.architecture.context.expression.ExpressionParser;

import com.architecture.context.exception.ServiceException;
import com.architecture.context.expression.ExpressionMetadata;
import com.architecture.context.lock.FailLock;
import com.architecture.context.lock.LockFactory;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.architecture.context.cache.CacheService;
import org.springframework.core.Ordered;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;

/**
 * @author luyi
 * 缓存operation处理基类
 * <li>对于实现类order排序,按照操作的频率排好序，增加程序效率：比如缓存读多，就把cacheable对应的处理器放最前边</li>
 */
public abstract class CacheOperationHandler implements Ordered {

    @Autowired(required = false)
    protected CacheService cacheService;

    @Autowired(required = false)
    private LockFactory lockFactory;
    @Autowired(required = false)
    protected ExpressionParser expressionParser;


    public Object value(String valueExpression, ExpressionMetadata expressionMetadata) {
        return expressionParser.parserExpression(expressionMetadata, valueExpression);
    }

    /**
     * operation是否匹配
     *
     * @param operation operation对应的缓存操作
     * @return 是否匹配，如果true就对这个operation的进行缓存处理
     */
    public abstract boolean match(BaseCacheOperation operation);


    /**
     * 得到缓存的key
     *
     * @param operation          注解操作
     * @param expressionMetadata 表达式元数据
     * @return 解析后的缓存key
     */
    protected List<String> getCacheKeys(BaseCacheOperation operation, ExpressionMetadata expressionMetadata) {
        List<Object> cacheNames = null;
        if (ArrayUtils.isNotEmpty(operation.getCacheName())) {
            cacheNames = expressionParser.parserFixExpression(expressionMetadata, operation.getCacheName());
        }
        String key = Objects.requireNonNull(expressionParser.parserExpression(expressionMetadata, operation.getKey())).toString();
        List<String> cacheKeys;
        if (cacheNames != null) {
            cacheKeys = new ArrayList<>(cacheNames.size());
            for (Object cacheName : cacheNames) {
                cacheKeys.add(cacheName + cacheService.getSplit() + key);
            }
        } else {
            cacheKeys = List.of(key);
        }
        return cacheKeys;
    }

    /**
     * 处理缓存operation
     *
     * @param operation
     * @param methodReturnValueFunction
     * @param expressionMetadata
     * @throws Throwable
     */
    public void handler(BaseCacheOperation operation, MethodReturnValueFunction methodReturnValueFunction, ExpressionMetadata expressionMetadata) throws Throwable {
        if (this.canHandler(operation, expressionMetadata, true)) {
            Lock lock = lockFactory.get(operation.getLocked(), expressionMetadata);
            if (lock == null) {
                this.execute(operation, expressionMetadata, methodReturnValueFunction);
            } else if (lock instanceof FailLock) {
                throw new ServiceException("获取锁失败");
            } else {
                try {
                    this.execute(operation, expressionMetadata, methodReturnValueFunction);
                } finally {
                    //释放锁
                    lock.unlock();
                }
            }
        }
    }

    /**
     * 能否处理
     *
     * @param excludeResult 是否排除#result
     * @return true标识condition满足，unless为false
     */
    protected boolean canHandler(BaseCacheOperation operation, ExpressionMetadata expressionMetadata, boolean excludeResult) {
        String condition = operation.getCondition(), unless = operation.getUnless();
        if (StringUtils.isBlank(condition) && StringUtils.isBlank(unless)) {
            return true;
        }
        if (StringUtils.isNotEmpty(condition)) {
            if (excludeResult && isContainsResult(condition)) {
                return true;
            }
            Object isCondition = expressionParser.parserExpression(expressionMetadata, operation.getCondition());
            if (!(isCondition instanceof Boolean)) {
                throw new IllegalArgumentException(MessageFormat.format("condition[{0}]有误,必须为Boolean类型", operation.getCondition()));
            }
            return (boolean) isCondition;
        }

        if (StringUtils.isNotEmpty(unless)) {
            if (excludeResult && isContainsResult(unless)) {
                return true;
            }
            Object isUnless = expressionParser.parserExpression(expressionMetadata, unless);
            if (!(isUnless instanceof Boolean)) {
                throw new IllegalArgumentException(MessageFormat.format("unless[{0}]有误,必须为Boolean类型", unless));
            }
            return !(boolean) isUnless;
        }
        return true;
    }

    private boolean isContainsResult(String expression) {
        return expression.contains("#result");
    }

    /**
     * 执行缓存处理
     *
     * @param operation                 缓存操作对应的数据
     * @param expressionMetadata        表达式元数据
     * @param methodReturnValueFunction 返回值功能函数
     */
    protected abstract void execute(BaseCacheOperation operation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable;


    public CacheOperationHandler setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
        return this;
    }

    public void setLockFactory(LockFactory lockFactory) {
        this.lockFactory = lockFactory;
    }

    public CacheOperationHandler setExpressionParser(ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
        return this;
    }

}
