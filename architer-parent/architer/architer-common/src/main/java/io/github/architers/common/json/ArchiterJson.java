package io.github.architers.common.json;

import java.lang.reflect.Type;

public interface ArchiterJson {
    /**
     * 转换为json字符串
     *
     * @param object 需要转jons的对象
     * @return json 字符串
     */
    String toJsonString(Object object);

    /**
     * 转为二进制字节码
     * @param object
     * @return
     */
    byte[] toJsonBytes(Object object);

    <T> T parseObject(byte[] bytes, Class<T> clazz);

    <T> T parseObject(String jsonStr, Class<T> clazz);


    <T> T parseObjectByType(String jsonStr, ArchiterTypeReference<T> typeReference);
}
