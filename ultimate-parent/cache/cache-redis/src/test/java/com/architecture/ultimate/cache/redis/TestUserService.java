package com.architecture.ultimate.cache.redis;


import com.architecture.ultimate.cache.redis.entity.User;
import com.architecture.ultimate.cache.redis.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@SpringBootTest
public class TestUserService {

    @Autowired
    private UserService userService;

    ExecutorService executorService = Executors.newFixedThreadPool(500);

    /**
     * 测试查询单次
     */
    @Test
    public void testFindById() {
        Long id = (long) (Math.random() * 100000);
        User user = userService.findById(id);
        System.out.println(user);
    }

    /**
     * 测试并发查询
     */
    @Test
    public void testConcurrentFindById() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        Long id = (long) (Math.random() * 100000);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> {
                try {
                    User user = userService.findById(id);
                    //System.out.println(user + ":" + id);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }

            });
        }
        countDownLatch.await();
    }

    @Test
    public void testUpdate() {
        User user = new User("user1", 555);
        user = userService.update(user);
        System.out.println(user);
    }

    /**
     * 测试读写锁
     */
    @Test
    public void testReadWriteUser() throws InterruptedException {
        String name = "user-read-write";
        CountDownLatch countDownLatch = new CountDownLatch(2);
        User user = new User(name, 555);
        executorService.submit(() -> {
            try {
                userService.update(user);
                System.out.println("update finish");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        });
        Thread.sleep(200);
        executorService.submit(() -> {
            try {
                userService.findByName(name);
                System.out.println("read finish");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        System.out.println(user);
    }

    /**
     * 测试删除数据
     */
    @Test
    public void testDeleteByName() {
        String name = "user1";
        userService.deleteByName(name);
    }

    /**
     * 测试@PutCache注解值
     */
    @Test
    public void testPutCacheCacheValue() {
        User user = new User("233", 555);
        userService.updateForCacheValue(user);
    }
}
