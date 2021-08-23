package com.architecture.test.cache.delete;

import com.architecture.context.cache.CacheService;
import com.architecture.test.cache.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * @author luyi
 * 测试DeleteCache注解
 */
@SpringBootTest
public class DeleteTest {
    @Autowired
    private DeleteService deleteService;
    @Autowired
    private CacheService cacheService;

    /**
     * 测试一个删除注解
     */
    @Test
    public void testOneDelete() {
        int count = 1000;
        for (int i = 0; i < count; i++) {
            UserInfo userInfo = UserInfo.getRandomUserInfo();
            cacheService.set("oneDelete" + cacheService.getSplit() + userInfo.getUsername(), userInfo);
            deleteService.oneDelete(userInfo);
        }
    }
}
