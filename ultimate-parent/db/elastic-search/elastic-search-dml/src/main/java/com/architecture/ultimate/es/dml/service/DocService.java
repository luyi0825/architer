package com.architecture.ultimate.es.dml.service;




import com.architecture.ultimate.es.model.doc.DocumentRequest;
import com.architecture.ultimate.es.model.doc.DocumentResponse;

import java.io.IOException;
import java.util.List;

/**
 * 数据查询的service
 *
 * @author luyi
 */
public interface DocService {

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

    /**
     * 批量操作
     *
     * @param documentRequests 多个文档操作请求
     * @throws IOException es-bulk操作抛出的异常
     */
    boolean bulk(List<DocumentRequest> documentRequests) throws IOException;

    /**
     * 单个操作
     *
     * @param documentRequest 单个文档操作请求
     * @throws IOException es-bulk操作抛出的异常
     */
    boolean bulkOne(DocumentRequest documentRequest) throws IOException;

}
