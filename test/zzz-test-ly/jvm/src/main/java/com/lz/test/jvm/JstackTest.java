package com.lz.test.jvm;

/**
 * Jstack测试
 *
 * @author luyi
 */
public class JstackTest {

    public static void main(String[] args) {
        while (true) {
            Math math = new Math();
            math.add();
        }
    }
}
