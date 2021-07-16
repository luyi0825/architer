package com.business.search.factory;

import com.core.es.model.RequestType;
import com.core.es.model.doc.DocumentRequest;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author luiy
 * es的indexRequest生成
 */
@Component
public class IndexRequestFactory implements FactorySupport<IndexRequest> {
    @Override
    public DocWriteRequest<IndexRequest> get(DocumentRequest documentRequest) {
        IndexRequest indexRequest = new IndexRequest(documentRequest.getIndex());
        if (!StringUtils.isEmpty(documentRequest.getId())) {
            indexRequest.id(documentRequest.getId());
        }
        indexRequest.source(documentRequest.getSource());
        return indexRequest;
    }

    @Override
    public boolean support(String requestType) {
        return RequestType.INDEX.name().equals(requestType);
    }
}
