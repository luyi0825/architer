package io.github.architers.center.dict.service;

import io.github.architers.center.dict.domain.dto.ImportJsonDict;

import java.util.List;

/**
 * 数据字典对应的service
 *
 * @author luyi
 */
public interface DictService {
    void importJsonDictData(List<ImportJsonDict> importJsonDictList);
}
