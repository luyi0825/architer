package com.lz.core.cache.redis;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试方法返回类型
 */
public class TestMethodType {

    @Test
    public void test() throws NoSuchMethodException {
      //  Method method = TestMethodType.class.getMethod("string");
        Method method = TestMethodType.class.getMethod("list");
        Class<?> returnType = method.getReturnType();
        System.out.println(returnType.getComponentType());
        System.out.println(returnType.getClass());
        System.out.println(returnType.getTypeParameters());
        //System.out.println(returnType.get);
    }

    public String string() {
        return "66";
    }

    public List<String> list() {
        List<String> list = new ArrayList<>(1);
        list.add("1");
        return list;
    }


}
