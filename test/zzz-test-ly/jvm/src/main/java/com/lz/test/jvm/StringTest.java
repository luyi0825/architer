package com.lz.test.jvm;


import org.junit.Test;

/**
 * @author luyi
 * 字符串测试
 */
public class StringTest {

    @Test
    public void test1() {
        String s0 = "zhuge";
        String s1 = new String("zhuge");
        String s2 = "zhu" + new String("ge");
        System.out.println(s0 == s1); // false
        System.out.println(s0 == s2); // false
        System.out.println(s1 == s2);
    }

    @Test
    public void test2() {
        String a = "a1";
        String b = "a" + 1;
        System.out.println(a == b); // true
        a = "atrue";
        b = "a" + "true";
        System.out.println(a == b); // true
        a = "a3.4";
        b = "a" + 3.4;
        System.out.println(a == b); // true
    }

    @Test
    public void test3() {
        String a = "ab";
        String bb = "b";
        String b = "a" + bb;
        System.out.println(a == b); // false
    }

    @Test
    public void test4() {
        String a = "ab";
        final String bb = "b";
        String b = "a" + bb;
        System.out.println(a == b); // true
    }

    @Test
    public void test6() {
        String a = "ab";
        final String bb = getBB();
        String b = "a" + bb;
        System.out.println(a == b); // false
    }

    private  String getBB() {
        return "b";
    }
}
