package io.github.architers.cache.entity;

import io.github.architers.context.autocode.AutoSqlField;
import lombok.Data;

@AutoSqlField
@Data
public class MyEntity {

    private String firstName;

    private String password;

    private String startTime;

    private String endTime;

    private String updateTime;


    public MyEntity() {

    }

    public static void main(String[] args) {
//        test(MyEntityFields.firstName);
//        test(MyEntityFields.endTime);
//        test(MyEntityFields.updateTime);
    }

    static void test(String name) {
        System.out.println(name);
    }

}
