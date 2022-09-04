package io.github.architers.cache.redisson;

import io.github.architers.context.cache.batch.CacheKey;
import io.github.architers.context.cache.batch.CacheValue;
import lombok.Data;

import java.io.Serializable;


@Data
@CacheValue
public class CacheUser implements Serializable {

    @CacheKey(order = 1)
    private String city;

    @CacheKey(order = 2)
    private String name;

    public String getName() {
        return name;
    }

    public CacheUser setName(String name) {
        this.name = name;
        return this;
    }
}
