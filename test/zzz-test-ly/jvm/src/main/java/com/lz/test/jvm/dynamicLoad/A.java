package com.lz.test.jvm.dynamicLoad;

public class A {
    static {
        System.out.println("A static");
    }

    public A() {
        System.out.println("A init");
    }
}
