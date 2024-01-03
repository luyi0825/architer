package io.github.architers.cache.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class FieldConvertCacheDTO implements Serializable {
    private String code;

    private String caption;
}
