package io.github.architers.context.cache.utils;

import io.github.architers.context.cache.annotation.CacheKey;
import io.github.architers.context.cache.annotation.CacheValue;
import io.github.architers.context.cache.model.CacheField;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 批量值的解析
 *
 * @author luyi
 * @version 1.0.0
 */
public class BatchValueUtils {
    /**
     * 表示值为当前对象
     */
    private static final String THIS_VALUE = "this";
    /**
     * 缓存类的缓存字段信息
     */
    static Map<String, CacheField> fieldCaches = new ConcurrentHashMap<>();


    /**
     * 通过反射得到对象的缓存字段信息
     *
     * @param clazz 对应的缓存对象的class类型
     * @return 缓存字段信息
     */
    public static CacheField getObjectCacheField(Class<?> clazz) {
        String className = clazz.getName();
        //从缓存中取值
        CacheField cacheField = fieldCaches.get(className);
        if (cacheField != null) {
            return cacheField;
        }
        cacheField = new CacheField();
        //是否解析到key和value
        boolean parsedValue = false;
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof CacheValue) {
                //缓存值为当前这个对象
                cacheField.setValue(THIS_VALUE);
                parsedValue = true;
            }
        }
        List<CacheField.CacheKeyInfo> cacheKeyInfos = new LinkedList<>();
        for (Field field : clazz.getDeclaredFields()) {
            annotations = field.getDeclaredAnnotations();
            if (annotations.length == 0) {
                continue;
            }
            for (Annotation annotation : annotations) {
                if (annotation instanceof CacheKey) {
                    CacheField.CacheKeyInfo cacheKeyInfo = new CacheField.CacheKeyInfo();
                    cacheKeyInfos.add(cacheKeyInfo);
                    cacheKeyInfo.setField(field);
                    cacheKeyInfo.setOrder(((CacheKey) annotation).order());
                }
                //缓存值
                if (annotation instanceof CacheValue && !parsedValue) {
                    cacheField.setValue(field.getName());
                    parsedValue = true;
                }
            }
        }

        if (CollectionUtils.isEmpty(cacheKeyInfos)) {
            throw new IllegalArgumentException(className + "中cacheKey不存在");
        }

        if (!StringUtils.hasText(cacheField.getValue())) {
            throw new IllegalArgumentException(className + "中cacheValue不存在");
        }
        Collections.sort(cacheKeyInfos);
        Field[] keys = cacheKeyInfos.stream().map(CacheField.CacheKeyInfo::getField).collect(Collectors.toList()).toArray(new Field[0]);
        cacheField.setKeys(keys);
        fieldCaches.putIfAbsent(className, cacheField);
        return fieldCaches.get(className);
    }

    /**
     * 得到缓存key的值
     */
    public static String getCacheKey(Object cacheEntity, String split) {
        if (cacheEntity == null) {
            return null;
        }
        if (cacheEntity instanceof String || cacheEntity instanceof Number) {
            return cacheEntity.toString();
        }
        CacheField cacheField = getObjectCacheField(cacheEntity.getClass());
        StringBuilder keyBuilder = new StringBuilder();
        int length = cacheField.getKeys().length;
        for (int i = 0; i < length; i++) {
            Field key = cacheField.getKeys()[i];
            Object value = getFieldValue(key, cacheEntity);
            if (value == null) {
                throw new RuntimeException(key + "的值为空");
            }
            keyBuilder.append(value);
            if (i != length - 1) {
                keyBuilder.append(split);
            }
        }
        return keyBuilder.toString();
    }


    public static Object getCacheValue(Object cacheEntity) {
        CacheField cacheField = getObjectCacheField(cacheEntity.getClass());
        if (THIS_VALUE.equals(cacheField.getValue())) {
            return cacheEntity;
        }
        return getFieldValue(cacheField.getValue(), cacheEntity);
    }

    /**
     * 得到对象的字段值
     *
     * @param field  字段
     * @param object 对象示例
     * @return 字段值
     */
    public static Object getFieldValue(Field field, Object object) {
        try {
            //暴力反射
            field.setAccessible(true);
            return ReflectionUtils.getField(field, object);
        } catch (Exception e) {
            throw new RuntimeException("获取字段值失败", e);
        }
    }


    /**
     * 得到对象的字段值
     *
     * @param fieldName 字段名称
     * @param object    对象示例
     * @return 字段值
     */
    public static Object getFieldValue(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //暴力反射
            field.setAccessible(true);
            return ReflectionUtils.getField(field, object);
        } catch (Exception e) {
            throw new RuntimeException("获取字段值失败", e);
        }
    }

    /**
     * 将值解析成map
     *
     * @return key为缓存的key, value为缓存的值
     */
    public static Map<String, Object> parseValue2Map(String cacheName, String split, Object value) {
        if (value instanceof Map) {
            Map<String, Object> cacheMap = new HashMap<>(((Map<?, ?>) value).size());
            ((Map<?, ?>) value).forEach((k, v) -> {
                String cacheKey = String.join(split, cacheName, k.toString());
                cacheMap.put(cacheKey, value);
            });
            return cacheMap;
        }
        //将list通过注解转map
        if (value instanceof Collection) {
            Collection<?> values = (Collection<?>) value;
            Map<String, Object> cacheData = new HashMap<>(values.size());
            for (Object o : values) {
                Object cacheKey = getCacheKey(o, split);
                Object cacheValue = getCacheValue(o);
                cacheData.put(String.join(split, cacheName, cacheKey.toString()), cacheValue);
            }
            return cacheData;
        }
        throw new IllegalArgumentException("cacheValue有误,必须属于map或者Collection");
    }


    /**
     * 将值解析成map
     */
    public static Map<Object, Object> parseValue2Map(Object value, String split) {
        if (value instanceof Map) {
            Map<Object, Object> cacheMap = new HashMap<>(((Map<?, ?>) value).size());
            cacheMap.putAll((Map<?, ?>) value);
            return cacheMap;
        }
        //将list通过注解转map
        if (value instanceof Collection) {
            Collection<?> values = (Collection<?>) value;
            Map<Object, Object> cacheData = new HashMap<>(values.size());
            for (Object o : values) {
                Object cacheKey = getCacheKey(o, split);
                Object cacheValue = getCacheValue(o);
                cacheData.put(cacheKey, cacheValue);
            }
            return cacheData;
        }
        throw new IllegalArgumentException("cacheValue有误,必须属于map或者Collection");
    }

    /**
     * 解析缓存key
     *
     * @param cacheName 缓存名称
     */
    public static Collection<Object> parseCacheKeys(String cacheName, String split, Object keys) {
        if (keys instanceof Collection) {
            Collection<Object> cacheKeys = new ArrayList<>(((Collection<?>) keys).size());
            ((Collection<?>) keys).forEach(key -> {
                if (key instanceof String || key instanceof Number) {
                    cacheKeys.add(String.join(split, cacheName, key.toString()));
                } else {
                    cacheKeys.add(String.join(split, cacheName, getCacheKey(key, split).toString()));
                }
            });
            return cacheKeys;
        }
        throw new IllegalArgumentException("keys类型不匹配");
    }


    /**
     * 解析批量驱逐的key
     *
     * @param value
     * @param split 缓存分隔符号
     * @return value对应的key
     */
    public static Collection<?> parseBatchEvictKeys(Object value, String split) {
        if (value instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) value;
            return map.keySet();
        }
        //将list通过注解转map
        if (value instanceof Collection) {
            Collection<?> data = (Collection<?>) value;
            Collection<Object> keys = new HashSet<>(data.size(), 1);
            for (Object o : data) {
                Object key = getCacheKey(o, split);
                keys.add(key);
            }
            return keys;

        }
        throw new IllegalArgumentException("cacheValue有误,必须属于map或者Collection");
    }

}
