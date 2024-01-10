package io.github.architers.common.json;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


/**
 * Jackson
 * <p>
 * ObjectMapper是线程安全的，可以减少对象频繁的创建
 *
 * @author luyi
 * @since 1.0.3
 */
public final class Jackson implements ArchiterJson {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final static Jackson JACKSON = new Jackson();

    /**
     * 防止被new
     */
    private Jackson() {

    }

    /**
     * Object转成Json字符串
     *
     * @return json字符串
     */
    public  String toJsonString(Object object) {
        if (object instanceof String) {
            return (String) object;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public  byte[] toJsonBytes(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T parseObject(byte[] bytes, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(bytes,clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T parseObject(String jsonStr, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr,clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArchiterJson getInstance() {
        return JACKSON;
    }

}
