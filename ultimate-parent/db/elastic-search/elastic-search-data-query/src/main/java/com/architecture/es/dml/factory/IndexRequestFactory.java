package com.architecture.es.dml.factory;

import com.architecture.es.model.doc.DocumentRequest;
import com.architecture.es.model.RequestType;
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
        if (StringUtils.hasText(documentRequest.getId())) {
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
