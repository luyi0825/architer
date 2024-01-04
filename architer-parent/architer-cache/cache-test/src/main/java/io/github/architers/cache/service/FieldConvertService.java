package io.github.architers.cache.service;

import io.github.architers.cache.entity.FieldConvertBothCache;
import io.github.architers.cache.entity.FieldConvertNoCache;
import io.github.architers.cache.entity.FieldConvertRemoteCache;

import java.util.List;

public interface FieldConvertService {

    List<FieldConvertNoCache> convertNoCacheList(List<String> codes);

    List<FieldConvertRemoteCache> convertRemoteCache(List<String> codes);

    List<FieldConvertBothCache> convertBothCache(List<String> codes);

    Object convertLocalCache(List<String> codes);
}
