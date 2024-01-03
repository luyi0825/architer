package io.github.architers.cache.service;

import io.github.architers.cache.entity.FieldConvertNoCache;

import java.util.List;

public interface FieldConvertService {

    List<FieldConvertNoCache> convertNoCacheList(List<String> codes);

}
