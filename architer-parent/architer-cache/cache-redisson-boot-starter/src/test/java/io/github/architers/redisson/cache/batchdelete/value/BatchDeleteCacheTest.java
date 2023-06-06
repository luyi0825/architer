package io.github.architers.redisson.cache.batchdelete.value;

import io.github.architers.redisson.cache.CacheUser;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;

/**
 * 测试批量删除
 *
 * @author luyi
 */
@SpringBootTest
public class BatchDeleteCacheTest {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private BatchDeleteService batchDeleteService;


    /**
     * 测试批量删除map
     */
    @Test
    public void batchDeleteMap() {
        Map<String, String> map = new HashMap<>();
        map.put("batchDeleteMap:1", "1");
        map.put("batchDeleteMap:2", "2");
        map.put("batchDeleteMap:3", "3");

        redissonClient.getBuckets().set(map);
        Map<String, String> cacheKeyInfo = new HashMap<>();
        cacheKeyInfo.put("1", "1");
        cacheKeyInfo.put("2", "2");
        cacheKeyInfo.put("3", "3");
        batchDeleteService.batchDeleteMap(cacheKeyInfo);
    }

    /**
     * 批量删除集合
     */
    @Test
    public void batchDeleteCollection() {
        List<CacheUser> cacheUsers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            CacheUser user = new CacheUser().setUsername(UUID.randomUUID().toString()).setCity(
                    "123456");
            cacheUsers.add(user);
        }
        for (CacheUser cacheUser : cacheUsers) {
            redissonClient.getBucket("batchDeleteCollection:" + cacheUser.getCity() + ":" + cacheUser.getUsername()).set(cacheUser);
        }
        batchDeleteService.batchDeleteCollection(cacheUsers);

    }


}
