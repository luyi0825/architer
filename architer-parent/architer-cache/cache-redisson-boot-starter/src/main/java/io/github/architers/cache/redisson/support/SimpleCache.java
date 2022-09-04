package io.github.architers.cache.redisson.support;


import io.github.architers.context.Symbol;
import io.github.architers.context.cache.batch.BatchValueFactory;
import io.github.architers.context.utils.JsonUtils;
import org.redisson.api.*;


import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
public class SimpleCache extends BaseRedissonCache {


    private RedisTemplateCacheService redisTemplateCacheService;

    private final RedissonClient redissonClient;

    BatchValueFactory batchValueParser = new BatchValueFactory();

    public SimpleCache(String cacheName, RedissonClient redissonClient, RedisTemplateCacheService redisTemplateCacheService) {
        super(cacheName);
        this.redissonClient = redissonClient;
        this.redisTemplateCacheService = redisTemplateCacheService;
    }

    @Override
    public void set(Object key, Object value) {
        redissonClient.getBucket(getCacheKey(key)).set(value);
    }

    @Override
    public Future<Boolean> multiSet(Object values) {
        Map<String, Object> cache = batchValueParser.parseValue2Map(cacheName, Symbol.COLON, values);
        RBuckets buckets = redissonClient.getBuckets();
        return buckets.trySetAsync(cache);
    }

    @Override
    public Future<Boolean> multiSet(Object values, long expire, TimeUnit timeUnit) {
        Map<Object, Object> cache = batchValueParser.parseValue2Map(values, Symbol.COLON);
        RBatch rBatch = redissonClient.createBatch();
        cache.forEach((key, value) -> {
            rBatch.getBucket(getCacheKey(key)).trySetAsync(value, expire, timeUnit);
        });
        rBatch.executeAsync();
        return null;
    }

    @Override
    public void set(Object key, Object value, long expire, TimeUnit timeUnit) {
        redissonClient.getBucket(getCacheKey(key)).set(value, expire, timeUnit);
    }

    @Override
    public boolean setIfAbsent(Object key, Object value) {
        return redisTemplateCacheService.setIfAbsent(getCacheKey(key), value);
    }

    @Override
    public boolean setIfAbsent(Object key, Object value, long expire, TimeUnit timeUnit) {
        //expire(Duration.ofMillis( timeUnit.toMillis(expire)))
        return redisTemplateCacheService.setIfAbsent(key, value, expire, timeUnit);
    }

    @Override
    public boolean setIfPresent(Object key, Object value) {
        return false;
    }

    @Override
    public boolean setIfPresent(Object key, Object value, long expire, TimeUnit timeUnit) {
        return false;
    }

    @Override
    public Object getAndSet(Object key, Object value) {
        return null;
    }

    @Override
    public Object get(Object key) {
        return redissonClient.getBucket(getCacheKey(key)).get();
    }

    @Override
    public Map<?, Object> multiGet(Set<Object> keys) throws ExecutionException, InterruptedException {
        RBuckets buckets = redissonClient.getBuckets();
        ArrayList<String> bucketKeys = new ArrayList<>(keys.size());
        for (Object key : keys) {
            bucketKeys.add(getCacheKey(key));
        }
        RFuture<Map<String, Object>> future = buckets.getAsync(bucketKeys.toArray(new String[0]));
        return future.get();
    }

    @Override
    public <T> T get(Object key, Class<T> clazz) {
        Object value = redissonClient.getBucket(getCacheKey(key)).get();
        if (value instanceof String) {
            return JsonUtils.readValue((String) value, clazz);
        }
        return (T) value;
    }

    @Override
    public boolean delete(Object key) {
        return redissonClient.getBucket(getCacheKey(key)).delete();
    }

    @Override
    public long multiDelete(Collection<Object> keys) {
        RBatch batch = redissonClient.createBatch();
        for (Object key : keys) {
            batch.getBucket(getCacheKey(key)).deleteAsync();
        }
        batch.execute();
        return 0;
    }

    @Override
    public Object getAll() {
        return null;
    }

    @Override
    public void clearAll() {

    }
}
