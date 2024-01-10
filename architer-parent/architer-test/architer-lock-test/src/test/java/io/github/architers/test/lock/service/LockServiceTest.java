package io.github.architers.test.lock.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.concurrent.CountDownLatch;


@SpringBootTest
class LockServiceTest {

    @Resource
    private LockService lockService;

    @Test
    void getLock() throws InterruptedException {

        String key1 = "key1";
        CountDownLatch countDownLatch = new CountDownLatch(2);

        long lockTime = 2000;
        new Thread(() -> {
            try {
                lockService.getLock(key1, lockTime);
            } finally {
                countDownLatch.countDown();
            }
        }
        ).start();

        new Thread(() -> {
            try {
                lockService.getLock(key1, lockTime);
            } finally {
                countDownLatch.countDown();
            }
        }
        ).start();
        countDownLatch.await();
        System.out.println("end:getLock");
    }

    /**
     * 测试读锁
     */
    @Test
    void getReadLock() throws InterruptedException {
        String key1 = "key1";
        long lockTime = 2000;
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            try {
                lockService.getReadLock(key1, lockTime);
            } catch (Exception e) {
                countDownLatch.countDown();
            } finally {
                countDownLatch.countDown();
            }
        }).start();
        new Thread(() -> {
            try {
                lockService.getReadLock(key1, lockTime);
            } catch (Exception e) {

            } finally {
                countDownLatch.countDown();
            }
        }).start();
        new Thread(() -> {
            try {
                lockService.getReadLock(key1, lockTime);
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                countDownLatch.countDown();
            }
        }).start();
        countDownLatch.await();
        System.out.println("end");
    }

    @Test
    void getWriteLock() throws InterruptedException {
        String key1 = "key1";
        long lockTime = 2000;
        CountDownLatch countDownLatch = new CountDownLatch(3);
        new Thread(() -> {
            try {
                lockService.getWriteLock(key1, lockTime);
            } catch (Exception e) {
                countDownLatch.countDown();
            } finally {
                countDownLatch.countDown();
            }
        }).start();
        new Thread(() -> {
            try {
                lockService.getWriteLock(key1, lockTime);
            } catch (Exception e) {

            } finally {
                countDownLatch.countDown();
            }
        }).start();
        new Thread(() -> {
            try {
                lockService.getWriteLock(key1, lockTime);
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                countDownLatch.countDown();
            }
        }).start();
        countDownLatch.await();
        System.out.println("end");
    }

    /**
     * 测试读写锁
     */
    @Test
    public void getReadWriteLock() throws InterruptedException {
        String key1 = "key1";
        long lockTime = 2000;
        CountDownLatch countDownLatch = new CountDownLatch(3);
        new Thread(() -> {
            try {
                lockService.getReadLock(key1, lockTime);
            } catch (Exception e) {
                countDownLatch.countDown();
            } finally {
                countDownLatch.countDown();
            }
        }).start();
        new Thread(() -> {
            try {
                lockService.getWriteLock(key1, lockTime);
            } catch (Exception e) {

            } finally {
                countDownLatch.countDown();
            }
        }).start();
        new Thread(() -> {
            try {
                lockService.getReadLock(key1, lockTime);
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                countDownLatch.countDown();
            }
        }).start();
        countDownLatch.await();
        System.out.println("end");
    }
}