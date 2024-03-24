package io.github.architers.common.json.support;


import com.alibaba.fastjson.JSON;
import io.github.architers.common.json.ArchiterJson;
import io.github.architers.common.json.ArchiterTypeReference;

/**
 * 阿里FastJson
 *
 * @author luyi
 * @since 1.0.3
 */
public final class FastJson implements ArchiterJson {

    private static final FastJson fastJson = new FastJson();


    @Override
    public String toJsonString(Object object) {
        return JSON.toJSONString(object);
    }

    @Override
    public byte[] toJsonBytes(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T parseObject(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }

    @Override
    public <T> T parseObject(String jsonStr, Class<T> clazz) {
        return JSON.parseObject(jsonStr, clazz);
    }

    @Override
    public <T> T parseObjectByType(String jsonStr, ArchiterTypeReference<T> typeReference) {
        return JSON.parseObject(jsonStr, typeReference.getType());
    }


    public static ArchiterJson getInstance() {
        return fastJson;
    }
}
