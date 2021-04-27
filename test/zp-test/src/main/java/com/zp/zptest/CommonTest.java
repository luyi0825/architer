package com.zp.zptest;

import com.zp.zptest.annotation.Column;
import com.zp.zptest.annotation.Table;
import com.zp.zptest.annotation.User;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoupei
 * @create 2021/4/4
 **/
public class CommonTest {
    public static void main(String[] args) throws Exception {
        try {
            System.out.println(111);
        } catch (Exception e) {
            System.out.println(222);
        } finally {
            System.out.println(333);
        }
        System.out.println(444);
    }

    private static String query(User user) {
        StringBuilder sb = new StringBuilder();
        //获取class
        Class c = user.getClass();
        //获取table名称
        if (!c.isAnnotationPresent(Table.class)){
            return null;
        }
        Table table = (Table) c.getAnnotation(Table.class);
        String tableName = table.value();
        System.out.println(tableName);
        //获取field名称
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields){
            if (field.isAnnotationPresent(Column.class)){
                Column column = field.getAnnotation(Column.class);
                System.out.print(column.value() + ":");
                //通过反射获取对象值
                String fieldName = field.getName();
                String methodName = "get"
                        + fieldName.substring(0,1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    Method method = c.getMethod(methodName);
                    System.out.println(method.invoke(user));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

}
