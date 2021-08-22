package com.architecture.context.cache.operation;


import com.architecture.context.cache.proxy.ReturnValueFunction;
import com.architecture.context.expression.ExpressionParser;

import com.architecture.context.exception.ServiceException;
import com.architecture.context.expression.ExpressionMetadata;
import com.architecture.context.lock.FailLock;
import com.architecture.context.lock.LockFactory;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.architecture.context.cache.CacheService;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;

/**
 * @author luyi
 * 缓存operation
 */
public abstract class CacheOperationHandler {

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
    public abstract boolean match(CacheOperation operation);


    protected List<String> getCacheKeys(CacheOperation operation, ExpressionMetadata expressionMetadata) {
        List<Object> cacheNames = null;
        if (ArrayUtils.isNotEmpty(operation.getCacheName())) {
            cacheNames = expressionParser.parserFixExpression(expressionMetadata, operation.getCacheName());
        }
        String key = Objects.requireNonNull(expressionParser.parserExpression(expressionMetadata, operation.getKey())).toString();
        List<String> cacheKeys;
        if (cacheNames != null) {
            cacheKeys = new ArrayList<>(cacheNames.size());
            for (Object cacheName : cacheNames) {
                cacheKeys.add(cacheName + ":" + key);
            }
        } else {
            cacheKeys = List.of(key);
        }
        return cacheKeys;
    }


    public void handler(CacheOperation operation, ReturnValueFunction returnValueFunction, ExpressionMetadata expressionMetadata) throws Throwable {
        if (this.canHandler(operation, expressionMetadata)) {
            Lock lock = lockFactory.get(operation.getLocked(), expressionMetadata);
            if (lock == null) {
                this.execute(operation, expressionMetadata, returnValueFunction);
            } else if (lock instanceof FailLock) {
                throw new ServiceException("获取锁失败");
            } else {
                try {
                    this.execute(operation, expressionMetadata, returnValueFunction);
                } finally {
                    //释放锁
                    lock.unlock();
                }
            }
        }
    }

    protected boolean canHandler(CacheOperation operation, ExpressionMetadata expressionMetadata) {
        if (StringUtils.isBlank(operation.getCondition()) && StringUtils.isBlank(operation.getUnless())) {
            return true;
        }
        if (StringUtils.isNotEmpty(operation.getCondition())) {
            Object isCondition = expressionParser.parserExpression(expressionMetadata, operation.getCondition());
            if (!(isCondition instanceof Boolean)) {
                throw new IllegalArgumentException(MessageFormat.format("condition[{0}]有误,必须为Boolean类型", operation.getCondition()));
            }
            return (boolean) isCondition;
        }
        if (StringUtils.isNotEmpty(operation.getUnless())) {
            Object isUnless = expressionParser.parserExpression(expressionMetadata, operation.getUnless());
            if (!(isUnless instanceof Boolean)) {
                throw new IllegalArgumentException(MessageFormat.format("unless[{0}]有误,必须为Boolean类型", operation.getCondition()));
            }
            return !(boolean) isUnless;
        }
        return true;
    }

    protected abstract void execute(CacheOperation operation, ExpressionMetadata expressionMetadata, ReturnValueFunction returnValueFunction) throws Throwable;


    public CacheOperationHandler setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
        return this;
    }

    public CacheOperationHandler setLockFactory(LockFactory lockFactory) {
        this.lockFactory = lockFactory;
        return this;
    }

    public CacheOperationHandler setExpressionParser(ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
        return this;
    }

}
