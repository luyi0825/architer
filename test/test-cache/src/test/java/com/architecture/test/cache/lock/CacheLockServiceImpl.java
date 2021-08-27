package com.architecture.test.cache.lock;

import com.architecture.context.cache.lock.Locked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheLockServiceImpl implements CacheLockService {
    private final Logger logger = LoggerFactory.getLogger(CacheLockServiceImpl.class);

    @Override
    @Locked(lockName = "#targetClass.test1().methodName", key = "#lockKey", tryTime = 1, timeUnit = TimeUnit.MINUTES)
    public void test1(String lockKey) {
        logger.info("执行test1");
    }
}
