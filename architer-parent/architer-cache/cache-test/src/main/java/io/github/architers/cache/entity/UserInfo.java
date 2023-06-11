package io.github.architers.cache.entity;

import io.github.architers.context.cache.annotation.CacheKey;
import io.github.architers.context.cache.annotation.CacheValue;
import jodd.util.StringUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


@CacheValue
public class UserInfo implements Serializable {
    @CacheKey(order = 1)
    private String username;
    @CacheValue
    private String password;

    @CacheKey(order = 2)
    private String phone;

    public static UserInfo getRandomUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(UUID.randomUUID().toString());
        userInfo.setPassword("testPassword");
        int num = (int) (Math.random() * 10);
        userInfo.setPhone(StringUtil.repeat((num + ""), 11));
        return userInfo;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(username, userInfo.username) && Objects.equals(password, userInfo.password) && Objects.equals(phone, userInfo.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, phone);
    }

    public String getPhone() {
        return phone;
    }

    public UserInfo setPhone(String phone) {
        this.phone = phone;
        return this;
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



