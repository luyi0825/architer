package io.github.architers.context.cache.fieldconvert.utils;

public final class DataConvertCacheNameUtils {
    public static String getCacheName(String converter) {
        return "dataConverter:" + converter;
    }
}
