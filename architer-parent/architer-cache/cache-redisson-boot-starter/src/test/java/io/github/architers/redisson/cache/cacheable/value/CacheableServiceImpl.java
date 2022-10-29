package io.github.architers.redisson.cache.cacheable.value;

import io.github.architers.redisson.cache.CacheUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service("CacheableCacheUserService")
public class CacheableServiceImpl implements CacheableService {
    private final Logger logger = LoggerFactory.getLogger(CacheableService.class);

    @Override
    public CacheUser oneCacheable(String userName) {
        logger.info("{}查询数据库", "oneCacheable");
        return new CacheUser().setUsername(userName);
    }

    @Override
    public CacheUser twoCacheable(String userName) {
        logger.info("{}查询数据库", "twoCacheable");
        return new CacheUser().setUsername(userName).setPhone("phone:" + userName);
    }

    @Override
    public CacheUser expireTime_never(String userName) {
        logger.info("{}查询数据库", "expireTime_1");
        return new CacheUser().setUsername(userName);
    }


    @Override
    public CacheUser expireTime_3_minutes(String userName) {
        logger.info("{}查询数据库", "expireTime_3");
        return new CacheUser().setUsername(userName);
    }

    @Override
    public CacheUser randomTime(String userName) {
        logger.info("{}查询数据库:userName是{}", "randomTime", userName);
        return new CacheUser().setUsername(userName);
    }

    @Override
    public CacheUser condition(String userName) {
        logger.info("{}查询数据库:userName是{}", "condition", userName);
        return new CacheUser().setUsername(userName);
    }

    @Override
    public CacheUser unless(String userName) {
        logger.info("{}查询数据库:userName是{}", "unless", userName);
        return new CacheUser().setUsername(userName);
    }

    private final AtomicInteger toGatherCount = new AtomicInteger(0);

    @Override
    public CacheUser toGather(String userName) {
        logger.info("{}查询数据库:userName是{},查询了{}次", "toGather", userName, toGatherCount.incrementAndGet());
        return new CacheUser().setUsername(userName);
    }

    private final AtomicInteger toLockGatherCount = new AtomicInteger(0);

    @Override
    public CacheUser testLockToGather(String userName) {
        logger.info("{}查询数据库:userName是{},查询了{}次", "toGather", userName, toLockGatherCount.incrementAndGet());
        return new CacheUser().setUsername(userName);
    }

}
