package com.lz.core.cache.common.utils;


import com.lz.core.cache.common.Constants;

/**
 * @author luyi
 * key过期工具类
 */
public class CacheUtils {


    public static long getExpireTime(long expireTime, long randomExpireTime) {
        if (Constants.NEVER_EXPIRE == expireTime) {
            return Constants.NEVER_EXPIRE;
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
