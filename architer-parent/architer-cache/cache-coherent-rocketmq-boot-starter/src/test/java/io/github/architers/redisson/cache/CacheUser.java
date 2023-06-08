package io.github.architers.redisson.cache;

import io.github.architers.context.cache.annotation.CacheKey;
import io.github.architers.context.cache.annotation.CacheValue;
import lombok.Data;

import java.io.Serializable;


@Data
@CacheValue
public class CacheUser implements Serializable {


    @CacheKey(order = 2)
    private String username;


    @CacheKey(order = 1)
    private String city;

    @CacheValue
    private String password;

    public String getUsername() {
        return username;
    }

    public CacheUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getCity() {
        return city;
    }

    public CacheUser setCity(String city) {
        this.city = city;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public CacheUser setPassword(String password) {
        this.password = password;
        return this;
    }
}
