package io.github.architers.lock.redisson.expand;

import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;

import java.util.Collections;

/**
 * 值比较
 *
 * @author luyi
 */
public class NumberCompare {


    private final RedissonClient redissonClient;

    public NumberCompare(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }


    /**
     * 大于等于就设置新的值
     *
     * @param value 设置的值
     * @param key   标识key
     * @return true标识就设置成功，false失败
     */
    public boolean geAndSet(String key, Number value) {
        String versionLockScript = "local currValue = redis.call('get', KEYS[1]); "
                + "if currValue == false or ARGV[1]>=currValue then "
                + "redis.call('set', KEYS[1], ARGV[1]); "
                + "return 1 "
                + "else "
                + "return 0 "
                + "end";
        return redissonClient.getScript().eval(RScript.Mode.READ_WRITE, versionLockScript,
                RScript.ReturnType.BOOLEAN, Collections.singletonList(key), value);
    }

    /**
     * 大于等于就设置新的值,并设置过期时间
     *
     * @param value         设置的值
     * @param key           标识key
     * @param expireSeconds 过期的秒数，必须大于0
     * @return true标识就设置成功，false失败
     */
    public boolean geAndSet(String key, Number value, int expireSeconds) {
        String versionLockScript = "local currValue = redis.call('get', KEYS[1]); "
                + "if currValue == false or ARGV[1]>=currValue then "
                + "redis.call('set',KEYS[1],ARGV[1],'EX',ARGV[2]); "
                + "return 1 "
                + "else "
                + "return 0 "
                + "end";
        return redissonClient.getScript().eval(RScript.Mode.READ_WRITE, versionLockScript,
                RScript.ReturnType.BOOLEAN, Collections.singletonList(key), value,
                expireSeconds);
    }

    /**
     * 大于就设置新的值,并设置过期时间
     *
     * @param value         设置的值
     * @param key           标识key
     * @param expireSeconds 过期的秒数，必须大于0
     * @return true标识就设置成功，false失败
     */
    public boolean gtAndSet(String key, Number value, int expireSeconds) {
        String versionLockScript = "local currValue = redis.call('get', KEYS[1]); "
                + "if currValue == false or ARGV[1]>currValue then "
                + "redis.call('set',KEYS[1],ARGV[1],'EX',ARGV[2]); "
                + "return 1 "
                + "else "
                + "return 0 "
                + "end";
        return redissonClient.getScript().eval(RScript.Mode.READ_WRITE, versionLockScript,
                RScript.ReturnType.BOOLEAN, Collections.singletonList(key), value,
                expireSeconds);
    }

    /**
     * 大于就设置新的值,并设置过期时间
     *
     * @param value         设置的值
     * @param key           标识key
     * @return true标识就设置成功，false失败
     */
    public boolean gtAndSet(String key, Number value) {
        String versionLockScript = "local currValue = redis.call('get', KEYS[1]); "
                + "if currValue == false or ARGV[1]>currValue then "
                + "redis.call('set',KEYS[1],ARGV[1]); "
                + "return 1 "
                + "else "
                + "return 0 "
                + "end";
        return redissonClient.getScript().eval(RScript.Mode.READ_WRITE, versionLockScript,
                RScript.ReturnType.BOOLEAN, Collections.singletonList(key), value);
    }

}
