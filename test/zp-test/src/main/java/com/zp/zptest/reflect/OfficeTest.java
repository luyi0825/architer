package com.zp.zptest.reflect;

/**
 * @author zhoupei
 * @create 2021/4/5
 **/
public class OfficeTest {
    public static void main(String[] args) {
        try {
//            Class c = Class.forName("com.zp.zptest.reflect.World");
            Class c = Class.forName("com.zp.zptest.reflect.Excel");
            OfficeAble officeAble = (OfficeAble) c.newInstance();
            officeAble.start();
            String name = c.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
