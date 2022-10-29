package io.github.architers.context.cache.batch;

import lombok.Data;

import java.lang.reflect.Field;

/**
 * 批量缓存的缓存字段
 *
 * @author luyi
 * @version 1.0.0
 */
public class CacheField {
    /**
     * key对应的字段(已经排好序的)
     */
    private Field[] keys;
    /**
     * value对应的字段
     */
    private String value;

    public Field[] getKeys() {
        return keys;
    }

    public CacheField setKeys(Field[] keys) {
        this.keys = keys;
        return this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Data
    public static class CacheKeyInfo implements Comparable<CacheKeyInfo> {
        private Field field;
        private int order;

        @Override
        public int compareTo(CacheKeyInfo o) {
            return this.getOrder() - o.getOrder();
        }
    }
}


