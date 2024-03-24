package io.github.architers.common.json.support;

import com.alibaba.fastjson2.JSON;
import io.github.architers.common.json.ArchiterJson;
import io.github.architers.common.json.ArchiterTypeReference;

/**
 * 阿里FastJson2
 *
 * @author luyi
 */
public final class FastJson2 implements ArchiterJson {

    private static final FastJson2 fastJson2 = new FastJson2();


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
        return fastJson2;
    }
}
