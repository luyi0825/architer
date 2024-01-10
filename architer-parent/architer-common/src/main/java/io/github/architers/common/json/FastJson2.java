package io.github.architers.common.json;

import com.alibaba.fastjson2.JSON;

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
        return JSON.parseObject(bytes,clazz);
    }

    @Override
    public <T> T parseObject(String jsonStr, Class<T> clazz) {
        return JSON.parseObject(jsonStr,clazz);
    }

    public static ArchiterJson getInstance() {
        return fastJson2;
    }
}
