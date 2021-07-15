package com.business.search.doc.service;
;
import com.core.es.model.doc.DocumentRequest;
import com.core.es.model.doc.DocumentResponse;

import java.io.IOException;
import java.util.List;

/**
 * 数据查询的service
 *
 * @author luyi
 */
public interface DocService {

    DocumentRequest insert(String index, DocumentRequest docs) throws IOException;

    DocumentResponse findById(String index, String id) throws IOException;

    List<DocumentResponse> queryList(String index) throws IOException;

    /**
     * @param index
     * @param documentRequest
     * @throws IOException
     */
    void update(String index, DocumentRequest documentRequest) throws IOException;
}
