package com.lz.test.jvm;

/**
 * @author luyi
 * 测试字符串不可变
 */
public class StringFinal {
    public static void main(String[] args) {
        String s = "a" + "b" + "c"; //就等价于String s = "abc";
        String a = "a";
        String b = "b";
        String c = "c";
        String s1 = a + b + c;
        System.out.println(s == s1);
    }
}
