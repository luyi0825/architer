package com.lz.core.cache.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lz.core.cache.common.annotation.Cacheable;
import com.lz.core.cache.common.annotation.DeleteCache;
import com.lz.core.cache.common.annotation.PutCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

/**
 * @author luyi
 */
@Service
public class CacheService {

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Cacheable(suffix = "#id")
    public User getXxx(String id, String xxx) {
        System.out.println("查询数据库");
        return new User("name" + 1, 666);
    }

    //@PutCache(spElSuffix = "#user.name + '_' +#user.age")
    @PutCache(suffix = "#user.name")
    public User updateUser(User user) {
        Cache cache = redisCacheManager.getCache("com.lz.core.cache.CacheService");
        return user;
    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String test = "{\n" +
                "@class:\"com.lz.core.cache.User\"\n" +
                "name:\"1\"\n" +
                "age:666\n" +
                "}";
        User user = objectMapper.readValue(test, User.class);
        System.out.println(user);


    }

    @DeleteCache
    public void delete(String id) {
        System.out.println("删除：" + id);
    }

    @PutCache
    public String put(String id) {
        return "xxx" + id;
    }
}
