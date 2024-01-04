package io.github.architers.context.cache.fieldconvert.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.architers.context.cache.fieldconvert.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 数据转换工具类
 * <li>为了引入依赖，所以用工具类进行转换调用</li>
 *
 * @author luyi
 */
@Slf4j
@Component
public class FieldConvertUtils implements ApplicationContextAware {

    private static volatile FieldConvertSupport fieldConvertSupport;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        fieldConvertSupport = applicationContext.getBean(FieldConvertSupport.class);
    }


    /**
     * 两级缓存转换简单的数据（本地缓存->远程缓存->DB）
     */
    public static void convert(Object data) {
        ConvertFieldParam convertFieldParam = ConvertFieldParam.builder()
                .build();
        getDataConvertDispatcher().convertData(data, convertFieldParam);
    }

    public static void convert(Object data, ConvertFieldParam convertFieldParam) {
        getDataConvertDispatcher().convertData(data, convertFieldParam);
    }


    /**
     * 通过临时缓存转换数据（临时缓存-远程缓存->数据库）
     * <li>临时缓存是通过本地缓存->远程缓存->数据库依次构建</li>
     */
    public static void convert(Object data, TempCache tempCache) {
        ConvertFieldParam convertFieldParam = ConvertFieldParam.builder()
                .tempCache(null)
                .build();
        getDataConvertDispatcher().convertData(data, convertFieldParam);
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
            public boolean isCanEvict() {
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

            @Override
            public boolean containsKey(String key) {
                return false;
            }
        };
    }

    private static FieldConvertSupport getDataConvertDispatcher() {
        return fieldConvertSupport;
    }
}
