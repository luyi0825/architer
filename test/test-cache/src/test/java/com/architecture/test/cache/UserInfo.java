package com.architecture.test.cache;

import java.io.Serializable;
import java.util.UUID;

public class UserInfo implements Serializable {
    private String username;
    private String password;

    public static UserInfo getRandomUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(UUID.randomUUID().toString());
        userInfo.setPassword("testPassword");
        return userInfo;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public UserInfo setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserInfo setPassword(String password) {
        this.password = password;
        return this;
    }
}
