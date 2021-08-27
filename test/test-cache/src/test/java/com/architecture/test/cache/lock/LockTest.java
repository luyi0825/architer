package com.architecture.test.cache.lock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class LockTest {
    @Autowired
    private CacheLockService cacheLockService;

    /**
     * 测试外部注解锁
     */
    @Test
    public void test1() {
        cacheLockService.test1(UUID.randomUUID().toString());
    }
}
