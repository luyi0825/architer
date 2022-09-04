package io.github.architers.lock.redisson;


import io.github.architers.context.lock.LockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.locks.Lock;

@SpringBootTest
class RedissonLockServiceImplTest {

    @Autowired(required = false)
    private LockService lockService;

    @Test
    void tryFairLock() throws Exception {
        Lock lock = lockService.tryFairLock("testLock");
        boolean tryed = lock.tryLock();
        System.out.println(tryed);
        lock.unlock();
    }
}