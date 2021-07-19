package com.core.cache.local;

import com.core.cache.local.properties.LocalCacheProperties;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author luyi
 * 本地缓存
 */
public class LocalCacheService {


    private LocalCacheProperties cacheProperties;

    /**
     * 数据,用ConcurrentHashMap线程的原因是线程安全，而且是key-value的形式
     */
    private LocalCache data;

    /**
     * 过期的key,考虑到给请求速率，所以用链表（链表做删除、新增快）
     */
    private volatile Set<String> expireKeys = new LinkedHashSet<>();

    private CacheEnduranceActuator cacheEnduranceActuator = new CacheEnduranceActuator();


    private LocalCacheService(LocalCacheProperties localCacheProperties) {
        this.cacheProperties = localCacheProperties;
        LocalCache localCache = cacheEnduranceActuator.readCaChe();
        if (localCache != null) {
            this.data = localCache;
        } else if (localCacheProperties.getInitialCapacity() > 0) {
            this.data = new LocalCache(localCacheProperties.getInitialCapacity());
        } else {
            this.data = new LocalCache(1);
        }
        this.cacheProperties = localCacheProperties;
    }


    /**
     * 构建本地缓存
     *
     * @param localCacheProperties 配置属性
     * @return 当地缓存对象
     */
    public static LocalCacheService build(LocalCacheProperties localCacheProperties) {
        return new LocalCacheService(localCacheProperties);
    }


    /**
     * @param key           string类型key
     * @param value         存放的值
     * @param effectiveTime 有效时间<0 说明不过期,单位秒
     */
    public Object put(String key, Object value, long effectiveTime) {
        if (effectiveTime < 0) {
            throw new IllegalArgumentException("effectiveTime必须大于0");
        }

        long expireTime = System.currentTimeMillis() + effectiveTime * 1000;
        return putExpireData(key, value, expireTime);
    }

    /**
     * @param key        string类型key
     * @param value      存放的值
     * @param expireDate 过期的时间
     */
    public Object put(String key, Object value, Date expireDate) {
        long expireTime = expireDate.getTime();
        if (expireTime < System.currentTimeMillis()) {
            throw new IllegalArgumentException("expireDate不合法");
        }
        return putExpireData(key, value, expireTime);
    }

    /**
     * 设置过期数据
     *
     * @param key        缓存的时间
     * @param value      缓存的值
     * @param expireTime 过期时间
     */
    private Object putExpireData(String key, Object value, long expireTime) {
        //需要数据转换
        CacheInfo cacheInfo = new CacheInfo(value, expireTime);
        return this.put(key, cacheInfo);
    }

    /**
     * 存放数据，默认永不过期
     *
     * @param key   缓存的key
     * @param value 缓存的value
     * @return 同ConCurrentHashMap
     */
    public Object put(String key, Object value) {
        int size = this.data.size();
        //说明达到了上限
        if (size > this.cacheProperties.getMaximumSize()) {
            //@TODO 上限处理
        }
        //说明存在过期时间,将key放入过期容器中
        if (value instanceof CacheInfo) {
            expireKeys.add(key);
        }
        return this.data.put(key, value);
    }


    /**
     * 描述：从缓存中删除数据
     *
     * @author luyi
     * @date 2021/3/10
     */
    public Object remove(String key) {
        Object object = this.data.remove(key);
        //从过期的容器中删除key
        if (object instanceof CacheInfo) {
            expireKeys.remove(key);
        }
        return object;
    }

    /**
     * 清理缓存
     */
    public void clear() {
        this.data.clear();
        this.expireKeys.clear();
    }


    public Set<String> keySet() {
        return this.data.keySet();
    }


    public boolean containsKey(String key) {
        return this.data.containsKey(key);
    }


    /**
     * 得到缓存数据
     *
     * @param key 缓存的key
     * @return 返回缓存中的数据
     */
    public Object get(String key) {
        Object object = this.data.get(key);
        if (object instanceof CacheInfo) {
            CacheInfo cacheInfo = (CacheInfo) object;
            if (cacheInfo.getExpireDate() <= System.currentTimeMillis()) {
                //说明已经过期
                this.remove(key);
                return null;
            }
            return cacheInfo.getData();
        }
        return object;
    }

    /**
     * 描述：如果缓存中不存在这个key,放入缓存
     *
     * @author luyi
     * @date 2021/3/10
     */
    public Object putIfAbsent(String key, Object value) {
        Object object = this.data.putIfAbsent(key, value);
        if (object instanceof CacheInfo) {
            this.expireKeys.add(key);
        }
        return object;
    }

    /**
     * 描述：从缓存中移除数据
     *
     * @author luyi
     * @date 2021/3/10
     */
    public boolean remove(String key, Object value) {
        boolean removed = this.data.remove(key, value);
        if (removed && value instanceof CacheInfo) {
            this.expireKeys.remove(key);
        }
        return removed;
    }


    /**
     * 描述：
     *
     * @author luyi
     * @date 2021/3/10
     * 清理缓存执行器
     */
    public class CleanCacheActuator {


        private CleanCacheExecutorService cleanCacheExecutorService;

        private LocalCacheService localCacheService;

        @Autowired
        public CleanCacheActuator(CleanCacheExecutorService cleanCacheExecutorService, LocalCacheService localCacheService) {
            this.cleanCacheExecutorService = cleanCacheExecutorService;
            this.localCacheService = localCacheService;
            //开始清理缓存
            startCleanCache();
        }

        private void startCleanCache() {
            for (; ; ) {
                try {
                    Thread.sleep(5_000);
                    //  localCacheService.
                } catch (Exception e) {
                    // LOG.error("清理缓存失败", e);
                }

            }
        }

    }

}
