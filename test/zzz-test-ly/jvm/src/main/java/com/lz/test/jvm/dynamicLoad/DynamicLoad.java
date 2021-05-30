package com.lz.test.jvm.dynamicLoad;

/**
 * @author luyi
 * 测试动态加载
 */
public class DynamicLoad {
    static {
        System.out.println("DynamicLoad  static");
    }

    public DynamicLoad() {
        System.out.println("DynamicLoad init");
    }

    public static void main(String[] args) {
        A a = new A();
         B b = new B();
     //   B b = null;
    }
}
