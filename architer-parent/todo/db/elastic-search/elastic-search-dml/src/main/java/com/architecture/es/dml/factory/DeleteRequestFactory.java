package com.architecture.es.dml.factory;

import io.github.architers.es.model.doc.DocumentRequest;
import io.github.architers.es.model.RequestType;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.springframework.stereotype.Component;

/**
 * 得到删除request
 *
 * @author luyi
 */
@Component
public class DeleteRequestFactory implements FactorySupport<DeleteRequest> {
    @Override
    public DocWriteRequest<DeleteRequest> get(DocumentRequest documentRequest) {
        return new DeleteRequest(documentRequest.getIndex(), documentRequest.getId());
    }

    @Override
    public boolean support(String requestType) {
        return RequestType.DELETE.name().equals(requestType);
    }
}
