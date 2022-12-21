package io.github.architers.context.cache.utils;


import io.github.architers.context.cache.CacheConstants;


/**
 * @author luyi
 * key过期工具类
 */
public class CacheUtils {


    /**
     * 得到过期时间（过期时间+随机的过期时间随机数生成）
     *
     * @param expireTime       过期时间
     * @param randomExpireTime 随机的过期时间
     * @return 缓存过期时间
     */
    public static long getExpireTime(long expireTime, long randomExpireTime) {
        if (CacheConstants.NEVER_EXPIRE == expireTime) {
            return CacheConstants.NEVER_EXPIRE;
        }
        if (expireTime < 0) {
            throw new IllegalArgumentException("expireTime is Illegal");
        }
        if (randomExpireTime > 0) {
            return expireTime + (long) (Math.random() * randomExpireTime);
        }
        return expireTime;
    }





}
