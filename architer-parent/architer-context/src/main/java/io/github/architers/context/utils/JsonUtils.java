package io.github.architers.context.utils;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;


import java.lang.reflect.Type;

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
        return JSON.toJSONString(object);
    }

    public static byte[] toJsonBytes(Object object) {
        return JSON.toJSONBytes(object);
    }

    /**
     * 将json转为对象
     *
     * @param bytes json字符串
     * @param clazz 反序列化的class
     * @param <T>   实体类型
     * @return 反序列化的实体
     */
    public static <T> T parseObject(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);

    }

    /**
     * 将json转为对象（用于泛型序列化）
     *
     * @param value json字符串
     * @param type  反序列化的类型
     * @param <T>   实体类型
     * @return 反序列化的实体
     */
    public static <T> T parseObject(String value, Type type) {
        return JSON.parseObject(value, type);
    }

    /**
     * 将json转为对象
     *
     * @param value json字符串
     * @param clazz 反序列化的class
     * @param <T>   实体类型
     * @return 反序列化的实体
     */
    public static <T> T parseObject(String value, Class<T> clazz) {
        return JSON.parseObject(value, clazz);
    }


    /**
     * 将Json字符串反序列化成list
     *
     * @param jsonStr 需要反序列化的字符串
     * @param clazz   list中的实体
     * @return 反序列化后的list
     */
    public static <T> List<T> parseListObject(String jsonStr, Class<T> clazz) {
        return JSONArray.parseArray(jsonStr, clazz);
    }

    /**
     * 将Json字符串反序列化成list(泛型的反序列化）
     *
     * @param bytes 需要反序列化的字符串对应的字节码
     * @param type  list中的实体
     * @return 反序列化后的list
     */
    public static <T> List<T> parseListObject(byte[] bytes, Type type) {
        return JSON.parseArray(bytes, type);
    }


    public static Map<String,Object> toMap(Object object) {
        return JSONObject.parseObject(JSON.toJSONString(object));
    }
}
