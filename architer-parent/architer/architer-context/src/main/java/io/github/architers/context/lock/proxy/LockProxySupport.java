package io.github.architers.context.lock.proxy;

import io.github.architers.common.expression.method.ExpressionMetadata;
import io.github.architers.common.expression.method.ExpressionParser;
import io.github.architers.context.exception.BusException;
import io.github.architers.context.lock.*;
import io.github.architers.context.lock.annotation.ExclusiveLock;
import io.github.architers.context.lock.annotation.ReadLock;
import io.github.architers.context.lock.annotation.WriteLock;
import io.github.architers.context.lock.eums.LockType;
import io.github.architers.context.lock.support.NoLock;
import io.github.architers.common.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class LockProxySupport implements ApplicationContextAware {

    @Resource
    private LockAnnotationsParser lockAnnotationsParser;

    @Resource
    private List<LockFactory> lockFactoryList;

    @Resource
    private LockProperties lockProperties;

    @Resource
    private ExpressionParser expressionParser;

    private ApplicationContext applicationContext;

    public Object executeProxy(ExpressionMetadata expressionMetadata, LockExecuteFunction executeFunction) throws Throwable {

        Collection<? extends Annotation> annotationList = lockAnnotationsParser.parse(expressionMetadata.getTargetMethod());
        if (CollectionUtils.isEmpty(annotationList)) {
            //执行方法
            return executeFunction.execute();
        }
        List<ArchiterLock> lockList = new ArrayList<>(annotationList.size());
        try {

            for (Annotation annotation : annotationList) {
                BaseLockParam lockParam = this.getLockParam(annotation, expressionMetadata);
                ArchiterLock architerLock = this.getArchiterLock((SingleLockParam) lockParam, expressionMetadata, annotation);
                if (architerLock instanceof NoLock) {
                    //不需要加锁
                    continue;
                }
                boolean tryLocked = architerLock.tryLock(lockParam.getTryTime(), lockParam.getLeaseTime(), lockParam.getTimeUnit());
                if (!tryLocked) {
                    return handlerFail(lockParam.getFailHandle(), expressionMetadata);
                }
                log.info("获取到锁:{}", JsonUtils.toJsonString(lockParam));
                lockList.add(architerLock);
            }
            //获取到锁，就执行业务
            return executeFunction.execute();
        } finally {
            for (ArchiterLock architerLock : lockList) {
                try {
                    //释放锁
                    architerLock.unlock();
                } catch (Exception e) {
                    log.error("释放锁失败");
                }
            }
        }
    }

    private BaseLockParam getLockParam(Annotation annotation, ExpressionMetadata expressionMetadata) {
        if (annotation instanceof ExclusiveLock) {
            ExclusiveLock exclusiveLock = (ExclusiveLock) annotation;
            String key = (String) expressionParser.parserExpression(expressionMetadata, exclusiveLock.key());
            SingleLockParam singleLockParam = SingleLockParam.builder().key(key).build();
            singleLockParam.setLockType(exclusiveLock.lockType());
            singleLockParam.setLockName(exclusiveLock.lockName());
            singleLockParam.setCondition(exclusiveLock.condition());
            singleLockParam.setTimeUnit(exclusiveLock.timeUnit());
            singleLockParam.setLeaseTime(exclusiveLock.leaseTime());
            singleLockParam.setTryTime(exclusiveLock.tryTime());
            return singleLockParam;
        } else if (annotation instanceof ReadLock) {
            ReadLock readLock = (ReadLock) annotation;
            String key = (String) expressionParser.parserExpression(expressionMetadata, readLock.key());
            SingleLockParam singleLockParam = SingleLockParam.builder().key(key).build();
            singleLockParam.setLockType(readLock.lockType());
            singleLockParam.setLockName(readLock.lockName());
            singleLockParam.setCondition(readLock.condition());
            singleLockParam.setTimeUnit(readLock.timeUnit());
            singleLockParam.setLeaseTime(readLock.leaseTime());
            singleLockParam.setTryTime(readLock.tryTime());
            return singleLockParam;
        } else if (annotation instanceof WriteLock) {
            WriteLock writeLock = (WriteLock) annotation;
            String key = (String) expressionParser.parserExpression(expressionMetadata, writeLock.key());
            SingleLockParam singleLockParam = SingleLockParam.builder().key(key).build();
            singleLockParam.setLockType(writeLock.lockType());
            singleLockParam.setLockName(writeLock.lockName());
            singleLockParam.setCondition(writeLock.condition());
            singleLockParam.setTimeUnit(writeLock.timeUnit());
            singleLockParam.setLeaseTime(writeLock.leaseTime());
            singleLockParam.setTryTime(writeLock.tryTime());
            return singleLockParam;
        }
        throw new IllegalArgumentException("not support:" + annotation);
    }

    /**
     * 获取到锁
     *
     * @param singleLockParam    单个锁的参数
     * @param expressionMetadata 方法的元信息
     * @param annotation         对应的注解
     * @return 对应的锁
     */
    private ArchiterLock getArchiterLock(SingleLockParam singleLockParam, ExpressionMetadata expressionMetadata, Annotation annotation) {
        if (StringUtils.hasText(singleLockParam.getCondition())) {
            boolean canLock = (boolean) expressionParser.parserExpression(expressionMetadata, singleLockParam.getCondition());
            if (!canLock) {
                return NoLock.getInstance();
            }
        }
        LockFactory lockFactory = getLockFactory(singleLockParam.getLockType());
        if (annotation instanceof ExclusiveLock) {
            return lockFactory.getExclusiveLock(singleLockParam.getLockName(), singleLockParam.getKey());
        } else if (annotation instanceof ReadLock) {
            return lockFactory.getReadLock(singleLockParam.getLockName(), singleLockParam.getKey());
        } else if (annotation instanceof WriteLock) {
            return lockFactory.getWriteLock(singleLockParam.getLockName(), singleLockParam.getKey());
        }
        throw new RuntimeException("不支持注解:" + annotation.toString());
    }

    private LockFactory getLockFactory(LockType lockTypeEnum) {
        String lockType = getLockType(lockTypeEnum);
        for (LockFactory lockFactory : lockFactoryList) {
            if (lockType.equals(lockFactory.getLockType())) {
                return lockFactory;
            }
        }
        throw new RuntimeException("lockType不支持:" + lockTypeEnum);
    }


    private String getLockType(LockType lockType) {
        if (LockType.DEFAULT.equals(lockType)) {
            return lockProperties.getDefaultLockType().getType();
        }
        return lockType.getType();
    }


    /**
     * 做失败后的事情
     *
     * @return 执行失败返回的参数
     */
    private Object handlerFail(String failHandle, ExpressionMetadata expressionMetadata) throws Throwable {
        //方法名称
        String methodName;
        if (StringUtils.hasText(failHandle)) {
            //回调的对象实例
            Object callBackTarget;
            if (failHandle.contains("#")) {
                callBackTarget = applicationContext.getBean(failHandle.split("#")[0]);
                methodName = failHandle.split("#")[1];
            } else {
                callBackTarget = expressionMetadata.getTarget();
                methodName = failHandle;
            }
            Method callMethod = callBackTarget.getClass().getDeclaredMethod(methodName, expressionMetadata.getTargetMethod().getParameterTypes());
            //反射回调
            return callMethod.invoke(callBackTarget, expressionMetadata.getArgs());
        }
        throw new BusException("请稍后再试");

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
