package com.architecture.es.dml.service;


import com.architecture.es.model.ddl.IndexMapping;

import java.io.IOException;

/**
 * IndexMapping对应的service
 *
 * @author luyi
 */
public interface IndexMappingService {
    /**
     * 创建indexMapping(index,mapping)
     *
     * @param indexMapping indexMapping信息
     * @return 是否创建成功
     */
    boolean createIndexMapping(IndexMapping indexMapping) throws IOException;

    /**
     * 重构indexMapping
     *
     * @param indexMapping indexMapping信息
     * @return 是否重构成功
     */
    boolean rebuildIndexMapping(IndexMapping indexMapping) throws IOException;
}
