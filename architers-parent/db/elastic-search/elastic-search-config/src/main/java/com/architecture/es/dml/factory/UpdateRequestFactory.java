package io.github.architers.es.dml.factory;


import io.github.architers.es.model.RequestType;
import io.github.architers.es.model.doc.DocumentRequest;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.stereotype.Component;

/**
 * 更新适配
 *
 * @author luyi
 */
@Component
public class UpdateRequestFactory implements FactorySupport<UpdateRequest> {
    @Override
    public DocWriteRequest<UpdateRequest> get(DocumentRequest documentRequest) {
        UpdateRequest updateRequest = new UpdateRequest(documentRequest.getId(), documentRequest.getIndex());
        updateRequest.doc(documentRequest.getSource());
        return updateRequest;
    }

    @Override
    public boolean support(String requestType) {
        return RequestType.UPDATE.name().equals(requestType);
    }
}
