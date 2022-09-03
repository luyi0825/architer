package com.architecture.es.dataquery.api;

import io.github.architers.es.model.doc.DocumentResponse;

import java.io.IOException;
import java.util.List;

public interface DocumentQueryService {
    /**
     * 通过id查询
     *
     * @param index 索引
     * @param id    主键
     * @return 文档数据
     * @throws IOException es中 GetRequest操作抛出的异常
     */
    DocumentResponse findById(String index, String id) throws IOException;

    /**
     * 查询集合（也就是查询所有）
     *
     * @param index 索引
     * @return 文档数据集合
     * @throws IOException es中client操作的异常
     */
    List<DocumentResponse> queryList(String index) throws IOException;
}
