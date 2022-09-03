package io.github.architers.contenxt.lock;


import io.github.architers.contenxt.expression.ExpressionMetadata;
import io.github.architers.contenxt.expression.ExpressionParser;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * @author luyi
 * 锁的简单工厂，根据锁的注解信息@Locked， 生成对应的锁
 */
public class LockFactory implements ApplicationContextAware {

    private ExpressionParser expressionParser;
    private Map<LockEnum, LockService> lockServiceMap;

    public Lock get(Locked locked, ExpressionMetadata expressionMetadata) throws Exception {
        if (locked == null || LockEnum.NONE.equals(locked.lock())) {
            return null;
        }
        String condition = locked.condition();
        if (StringUtils.hasText(condition)) {
            Object canLock = expressionParser.parserExpression(expressionMetadata, condition);
            if (!(canLock instanceof Boolean)) {
                throw new IllegalArgumentException("condition有误");
            }
            if (canLock.equals(Boolean.FALSE)) {
                return null;
            }
        }
        LockService lockService = lockServiceMap.get(locked.lock());
        String lockName = expressionParser.parserFixExpressionForString(expressionMetadata, locked.lockName());
        if (StringUtils.hasText(locked.key())) {
            String key = (String) expressionParser.parserExpression(expressionMetadata, locked.key());
            lockName = String.join(lockService.getLockSplit(), lockName, key);
        }
        LockType lockType = locked.lockType();
        if (LockType.READ.equals(lockType)) {
            return lockService.tryReadLock(lockName, locked.tryTime(), locked.timeUnit());
        } else if (LockType.WRITE.equals(lockType)) {
            return lockService.tryWriteLock(lockName, locked.tryTime(), locked.timeUnit());
        } else if (LockType.REENTRANT_FAIR.equals(lockType)) {
            return lockService.tryFairLock(lockName, locked.tryTime(), locked.timeUnit());
        } else if (LockType.REENTRANT_UNFAIR.equals(lockType)) {
            return lockService.tryUnfairLock(lockName, locked.tryTime(), locked.timeUnit());
        } else {
            return LockService.FAIL_LOCK;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, LockService> lockServiceMap = applicationContext.getBeansOfType(LockService.class);
        if (CollectionUtils.isEmpty(lockServiceMap)) {
            return;
        }
        this.lockServiceMap = new HashMap<>(lockServiceMap.size());
        lockServiceMap.forEach((key, value) -> {
            if (key.startsWith("redis")) {
                this.lockServiceMap.put(LockEnum.REDIS, value);
            }
            this.lockServiceMap.put(LockEnum.DEFAULT, value);
        });
    }


    public void setExpressionParser(ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }

}
