package io.github.architers.context.cache.fieldconvert.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.architers.context.cache.fieldconvert.DataConvertDispatcher;
import io.github.architers.context.cache.fieldconvert.TempCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 数据转换工具类
 *
 * @author luyi
 */
@Slf4j
public class DataConvertUtils {

    private static volatile DataConvertDispatcher dataConvertDispatcher;


    /**
     * 最大的深度
     */
    private static final int MAX_DEPTH = 5;

    /**
     * 两级缓存转换简单的数据（本地缓存->远程缓存->DB）
     */
    public static void convertSimpleData(Object data) {
        long startTime = System.currentTimeMillis();
        int depth = 1;
        AtomicLong cost = new AtomicLong();
        if (data instanceof Collection) {
            //集合处理
            ((Collection<?>) data).forEach(e -> convertCollectionData(e, depth, cost, null));
        } else {
            convertData(data, depth, cost, null, false);
        }
        long time = System.currentTimeMillis() - startTime;
        if (time > 1000) {
            log.error("转换数据耗时:" + time);
        } else {
            log.info("转换数据耗时:{}", time);
        }
    }

    /**
     * 获取最大容量的过期临时缓存（防止本地缓存的数据太多，造成OOM）
     *
     * @param maximumSize     缓存最大的size
     * @param initialCapacity 初始化容量
     */
    public static TempCache getMaximumSizeExpireTempCache(long maximumSize, int initialCapacity) {
        Cache<String, Object> cache = Caffeine.newBuilder().maximumSize(maximumSize).initialCapacity(initialCapacity).build();
        return new TempCache() {
            @Override
            public boolean isCanExpire() {
                return true;
            }

            @Override
            public Object get(String key) {
                return cache.getIfPresent(key);
            }

            @Override
            public void put(String key, Object value) {
                cache.put(key, value);
            }

            @Override
            public void batchPut(Map<String, Object> value) {
                cache.putAll(value);
            }


        };
    }


    public static void convertManyDataWithTempCache(Object data, TempCache tempCache) {
        long startTime = System.currentTimeMillis();
        int depth = 1;
        AtomicLong cost = new AtomicLong();
        convertData(data, depth, cost, tempCache, false);
        long time = System.currentTimeMillis() - startTime;
        if (time > 2000) {
            log.error("转换数据耗时:" + time);
        } else {
            log.info("转换数据耗时:{}", time);
        }

    }

    public static void convertManyDataWithTempCacheExpire(Object data) {
        long startTime = System.currentTimeMillis();
        int depth = 1;

        AtomicLong cost = new AtomicLong();
        TempCache tempCache = getMaximumSizeExpireTempCache(2000, 1000);
        convertData(data, depth, cost, tempCache, false);
        long time = System.currentTimeMillis() - startTime;
        if (time > 2000) {
            log.error("转换数据耗时:" + time);
        } else {
            log.info("转换数据耗时:{}", time);
        }
    }

    public static void convertManyDataWithTempCacheNotExpire(Object data) {
        long startTime = System.currentTimeMillis();
        int depth = 1;
        Map<String, Object> tempCache = new HashMap<>(128);
        AtomicLong cost = new AtomicLong();
        TempCache tempCacheFunction = new TempCache() {
            @Override
            public boolean isCanExpire() {
                return false;
            }

            @Override
            public Object get(String key) {
                return tempCache.get(key);
            }

            @Override
            public void put(String key, Object value) {
                tempCache.put(key, value);
            }

            @Override
            public void batchPut(Map<String, Object> value) {
                tempCache.putAll(value);
            }
        };
        convertData(data, depth, cost, tempCacheFunction, false);
        long time = System.currentTimeMillis() - startTime;
        if (time > 2000) {
            log.error("转换数据耗时:" + time);
        } else {
            log.info("转换数据耗时:{}", time);
        }
    }


    private static void convertCollectionData(Object data, int depth, AtomicLong cost, TempCache tempCache) {
        if (CollectionUtils.isEmpty((Collection<?>) data)) {
            //数据为空
            return;
        }
        ++depth;
        parseTempCacheManyCacheValue(data, tempCache);
        int finalDepth = depth;
        ((Collection<?>) data).forEach(e -> convertData(e, finalDepth, cost, tempCache, true));

    }


    /**
     * @param tempCacheParsed 临时缓存已经解析
     */
    private static void convertData(Object data, int depth, AtomicLong cost, TempCache tempCache, boolean tempCacheParsed) {
        if (data == null) {
            return;
        }
        if (++depth > MAX_DEPTH) {
            throw new IllegalStateException("超过最大深度:" + MAX_DEPTH);
        }
        if (data instanceof Collection) {
            convertCollectionData(data, depth, cost, tempCache);
        } else {
            //不是集合的就进行字段转换
            for (Field declaredField : data.getClass().getDeclaredFields()) {
                if (declaredField.getAnnotation(NeedFieldConvert.class) != null) {
                    try {
                        declaredField.setAccessible(true);
                        Object fieldData = declaredField.get(data);
                        if (fieldData != null) {
                            convertData(fieldData, depth, cost, tempCache, false);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            dataConvertDispatcher = getDataConvertDispatcher();
            if (tempCache == null) {
                dataConvertDispatcher.convertData(data);
            } else {
                if (!tempCacheParsed) {
                    parseTempCacheManyCacheValue(data, tempCache);
                }
                dataConvertDispatcher.convertManyDataWithCache(data, tempCache);
            }

        }
    }

    private static void parseTempCacheManyCacheValue(Object data, TempCache tempCache) {
        if (tempCache == null) {
            return;
        }
        long startTime = System.currentTimeMillis();
        Map<String/*converter*/, Map<CacheLevel, Set<String>>> keyValueMap = new HashMap<>();
        if (data instanceof Collection) {
            ((Collection<?>) data).forEach(e -> parseTempCacheKeyInfo(e, keyValueMap));
        } else {
            parseTempCacheKeyInfo(data, keyValueMap);
        }
        log.info("解析临时缓存key耗时:" + (System.currentTimeMillis() - startTime));
        startTime = System.currentTimeMillis();
        OriginValueObtainSupport originValueObtainSupport = getDataConvertDispatcher().getOriginValueObtainSupport();
        keyValueMap.forEach((converter, cacheLevelMap) -> cacheLevelMap.forEach((cacheLevel, keys) -> {
            Map<String, Serializable> valueMap;
            Collection<String> notMatchKeys = null;
            if (CacheLevel.none.equals(cacheLevel)) {
                valueMap = originValueObtainSupport.getConvertOriginValueObtain(converter).getConvertOriginDatas(keys);
            } else if (CacheLevel.remote.equals(cacheLevel)) {
                Map<String, Serializable> remoteValueMap = originValueObtainSupport.getMapFromRemote(converter, keys);
                notMatchKeys = org.apache.commons.collections4.CollectionUtils.disjunction(keys, remoteValueMap.keySet());
                valueMap = remoteValueMap;
            } else if (CacheLevel.local.equals(cacheLevel)) {
                Map<String, Serializable> localValueMap = originValueObtainSupport.getMapFromLocal(converter, keys);
                notMatchKeys = org.apache.commons.collections4.CollectionUtils.disjunction(keys, localValueMap.keySet());
                valueMap = localValueMap;
            } else {
                throw new RuntimeException("cacheLevel错误");
            }
            if (notMatchKeys != null && !CollectionUtils.isEmpty(notMatchKeys)) {
                //本地缓存或者remote缓存都没，从数据库中查询
                Map<String, Serializable> dbMap = originValueObtainSupport.getConvertOriginValueObtain(converter).getConvertOriginDatas(notMatchKeys);
                if (!CollectionUtils.isEmpty(dbMap)) {
                    //将db中查询的数据，放到缓存中
                    valueMap.putAll(dbMap);
                }
                //异步放到缓存
                getDataConvertDispatcher().asyncPutCache(converter, dbMap, cacheLevel);
            }
            valueMap.forEach((key, value) -> {
                String tempCacheKey = getDataConvertDispatcher().getTempCacheKey(cacheLevel, converter, key);
                tempCache.put(tempCacheKey, value);
            });

        }));
        log.info("准备临时缓存数据耗时:" + (System.currentTimeMillis() - startTime));
    }

    private static void parseTempCacheKeyInfo(Object data, Map<String, Map<CacheLevel, Set<String>>> keyValueMap) {
        ClassFieldsCache.groupByOriginValueIndexAndCacheLevel(data.getClass()).forEach((index, cacheLevelListMap) -> {
            cacheLevelListMap.forEach((cacheLevel, fieldContexts) -> {
                for (FieldConvertContext fieldContext : fieldContexts) {
                    Map<CacheLevel, Set<String>> cacheLevelKeysMap = keyValueMap.computeIfAbsent(fieldContext.getOriginValueConverter(), k -> new HashMap<>());
                    String value = fieldContext.getSourceFieldStrValue(data);
                    CacheLevel cacheLevelKey = getDataConvertDispatcher().getTempCacheLevelKey(cacheLevel);
                    Set<String> converterKeys = cacheLevelKeysMap.computeIfAbsent(cacheLevelKey, k -> new HashSet<>());
                    if (value != null) {
                        converterKeys.add(value);
                    }
                }
            });
        });
    }

    private static DataConvertDispatcher getDataConvertDispatcher() {
        if (dataConvertDispatcher == null) {
            synchronized (DataConvertUtils.class) {
                if (dataConvertDispatcher == null) {
                    dataConvertDispatcher = SpringUtils.getBean(DataConvertDispatcher.class);
                }
            }
        }
        return dataConvertDispatcher;
    }
}
