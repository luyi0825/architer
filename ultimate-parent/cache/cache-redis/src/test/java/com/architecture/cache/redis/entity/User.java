package com.architecture.cache.redis.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luyi
 */
@Data
public class User implements Serializable {
    private String name;
    private int age;

    public User() {

    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
