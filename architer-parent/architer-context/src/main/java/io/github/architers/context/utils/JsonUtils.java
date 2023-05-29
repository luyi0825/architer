package io.github.architers.context.utils;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.lang.reflect.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Json工具类
 * <p>
 * 创建这个类的目的是:
 * 1.调用这无需关心Json序列化的api是什么，如果更换序列化工具，无需更改大量的序列化
 * 2.ObjectMapper是线程安全的，可以减少对象频繁的创建
 *
 * @author luyi
 */
public final class JsonUtils {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 防止被new
     */
    private JsonUtils() {

    }

    /**
     * Object转成Json字符串
     *
     * @return json字符串
     */
    public static String toJsonString(Object object) {
        if (object instanceof String) {
            return (String) object;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] toJsonBytes(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json转为对象
     *
     * @param bytes json字符串
     * @param clazz 反序列化的class
     * @param <T>   实体类型
     * @return 反序列化的实体
     */
    public static <T> T readValue(byte[] bytes, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(bytes, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 将json转为对象
     *
     * @param value json字符串
     * @param clazz 反序列化的class
     * @param <T>   实体类型
     * @return 反序列化的实体
     */
    public static <T> T readValue(String value, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(value, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectMapper objectMapper() {
        return OBJECT_MAPPER;
    }


    public static Map<String, Object> toMap(Object object) {
        return OBJECT_MAPPER.convertValue(object, Map.class);
    }
}
