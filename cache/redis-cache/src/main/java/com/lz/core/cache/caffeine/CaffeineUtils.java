//package com.ly.core.cache;
//
//
//import com.github.benmanes.caffeine.cache.Cache;
//import com.github.benmanes.caffeine.cache.Caffeine;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Component;
//
//
//import java.util.function.Function;
//
///**
// * 描述：Caffeine缓存工具类
// * * 其实也可以用延迟对列来做过期，但是有点缺点：
// * * 1.有大量的缓存设置了过期时间，占内存
// * * 2.当服务挂了，采用单纯的延迟对列，就找不到过期时间
// * * 3.存到其他持久化的地方,很麻烦，过期了一个key,立即将这个key放到缓存，对是否删除很难判断
// * *
// * * 所以就用了一个对象来记录过期时间，然后定时任务取数据，然后和当前时间对比，判断当前
// * * 缓存是否过期
// *
// * @author luyi
// * @date 2020/12/22
// */
//@Component
//public class CaffeineUtils {
//
//
//    public CaffeineUtils() {
//
//    }
//
//    /**
//     * 永不过期
//     */
//    private static final long NEVER_EXPIRE = -1;
//
//    private static Cache<String, CaffeineInfo> cache = Caffeine.newBuilder()
//            .maximumSize(10_000)
//            .build();
//
//
//    public Cache<String, CaffeineInfo> getCache() {
//        // return cache.get;
//        return null;
//    }
//
//
//    /**
//     * 描述：得到缓存值，
//     *
//     * @return 为空表示没有
//     * @author luyi
//     * @date 2020/12/22
//     */
//    public Object get(String cacheKey, Function<String, CaffeineInfo> function) {
//        if (StringUtils.isBlank(cacheKey)) {
//            return null;
//        }
//        CaffeineInfo cacheValue;
//        if (function == null) {
//            cacheValue = cache.getIfPresent(cacheKey);
//        } else {
//            cacheValue = cache.get(cacheKey, function);
//        }
//        if (cacheValue == null) {
//            return null;
//        }
//        //说明数据需要过期，直接返回null
//        if (cacheValue.getExpireTime() > System.currentTimeMillis()) {
//            return null;
//        }
//        return cacheValue.getCacheData();
//    }
//
//    /**
//     * 描述：想缓存中添加值
//     *
//     * @param expireTime 过期时间：秒
//     * @author luyi
//     * @date 2020/12/22
//     */
//    public boolean set(String key, Object value, long expireTime) {
//        if (StringUtils.isBlank(key)) {
//            throw new IllegalArgumentException("key is null!");
//        }
//        if (value == null) {
//            throw new IllegalArgumentException("value is null!");
//        }
//        CaffeineInfo caffeineInfo = new CaffeineInfo();
//        if (expireTime == NEVER_EXPIRE) {
//            caffeineInfo.setExpireTime(NEVER_EXPIRE);
//        } else {
//            caffeineInfo.setExpireTime(System.currentTimeMillis() + expireTime * 1000);
//        }
//        caffeineInfo.setCacheData(value);
//        cache.put(key, caffeineInfo);
//        return true;
//    }
//
//    /**
//     * 描述：删除缓存值
//     *
//     * @author luyi
//     * @date 2020/12/22
//     */
//    public void remove(String key) {
//        cache.invalidate(key);
//    }
//
//
//}
