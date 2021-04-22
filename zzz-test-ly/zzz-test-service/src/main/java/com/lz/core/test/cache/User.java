package com.lz.core.test.cache;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luyi
 */
@Data
public class User implements Serializable {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
