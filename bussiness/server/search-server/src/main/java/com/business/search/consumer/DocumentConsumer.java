package com.business.search.consumer;

import com.business.search.doc.service.DocService;
import com.business.search.factory.RequestUtils;
import com.core.es.model.EsConstant;
import com.core.es.model.doc.DocumentRequest;
import com.core.es.model.doc.SyncDocumentDTO;
import com.core.utils.JsonUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author luyi
 */
@Component
public class DocumentConsumer {
    private DocService docService;

    @RabbitListener(queuesToDeclare = {@Queue(name = EsConstant.QUEUE_SYNC_ES_DOCUMENT)})
    public void handler(Message message) throws IOException {
        List<SyncDocumentDTO> syncDocumentDTOList = JsonUtils.readListValue(message.getBody(), SyncDocumentDTO.class);
        for (SyncDocumentDTO syncDocumentDTO : syncDocumentDTOList) {
            List<DocumentRequest> documentRequests = syncDocumentDTO.getDocs();
            BulkRequest bulkRequest = new BulkRequest();
            docService.bulk(documentRequests);
        }
    }

    @Autowired
    public void setDocService(DocService docService) {
        this.docService = docService;
    }

}
