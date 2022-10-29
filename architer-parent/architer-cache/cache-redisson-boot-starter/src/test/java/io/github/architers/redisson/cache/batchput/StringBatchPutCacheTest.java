package io.github.architers.redisson.cache.batchput;

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
public class StringBatchPutCacheTest {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private StringBatchPutCacheService stringBatchPutCacheService;


    /**
     * 测试批量删除map
     */
    @Test
    public void batchPutMap() {

        Map<String, String> cacheInfo = new HashMap<>();
        cacheInfo.put("1", "1");
        cacheInfo.put("2", "2");
        cacheInfo.put("3", "3");
        //   redissonClient.getMapCache("batchPutMap").putAll(cacheInfo);

        stringBatchPutCacheService.batchPutMap(cacheInfo);
    }

    /**
     * 批量删除集合
     */
    @Test
    public void batchPutCollection() {
        List<CacheUser> cacheUsers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            CacheUser user = new CacheUser().setUsername(UUID.randomUUID().toString()).setCity(
                    "123456");
            cacheUsers.add(user);
        }
        stringBatchPutCacheService.batchPutCollection(cacheUsers);
    }


}
