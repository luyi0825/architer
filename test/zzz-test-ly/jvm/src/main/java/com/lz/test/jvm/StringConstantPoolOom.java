package com.lz.test.jvm;


import java.util.ArrayList;

/**
 * jdk6：‐Xms6M ‐Xmx6M ‐XX:PermSize=6M ‐XX:MaxPermSize=6M
 * jdk8：‐Xms6M ‐Xmx6M ‐XX:MetaspaceSize=6M ‐XX:MaxMetaspaceSize=6M
 * 字符串常量池OOM测试
 *
 * @author luyi
 */
public class StringConstantPoolOom {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            String str = String.valueOf(i).intern();
            list.add(str);
        }
    }
}