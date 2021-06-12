package com.lz.test.jvm;

import org.openjdk.jol.info.ClassLayout;

/**
 * 计算对象大小
 * -XX:-UseCompressedOops -XX:-UseCompressedClassPointers
 *
 * @author luyi
 */
public class JOLSample {
    // ‐XX:+UseCompressedOops 默认开启的压缩所有指针
    // ‐XX:+UseCompressedClassPointers 默认开启的压缩对象头里的类型指针Klass Pointer
    // Oops : Ordinary Object Pointers
    public static class A {
        //8B mark word
        //4B Klass Pointer 如果关闭压缩‐XX:‐UseCompressedClassPointers或‐XX:‐UseCompressedOops，则占用8B
        long id;
        //4B 如果关闭压缩‐XX:‐UseCompressedOops，则占用8B
        String name;
        //1B
        byte b;
        //4B 如果关闭压缩‐XX:‐UseCompressedOops，则占用8B
        Object o;
    }

    public static void main(String[] args) {
        ClassLayout layout = ClassLayout.parseInstance(new Object());
        System.out.println(layout.toPrintable());

        System.out.println();
        ClassLayout layout1 = ClassLayout.parseInstance(new int[]{});
        System.out.println(layout1.toPrintable());

        System.out.println();
        ClassLayout layout2 = ClassLayout.parseInstance(new A());
        System.out.println(layout2.toPrintable());
    }
}


