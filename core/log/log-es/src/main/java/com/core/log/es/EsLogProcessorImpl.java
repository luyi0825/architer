package com.core.log.es;


import com.core.es.model.EsConstant;
import com.core.es.model.ddl.IndexMapping;
import com.core.es.model.doc.DocumentRequest;
import com.core.es.model.doc.SyncDocumentRequest;
import com.core.log.common.LogMeta;
import com.core.log.common.enums.LogType;
import com.core.log.common.service.LogProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author luyi
 */
@Component
public class EsLogProcessorImpl extends LogProcessor {
    private RabbitTemplate rabbitTemplate;

    @Override
    public boolean support(LogType logType) {
        return LogType.ES.equals(logType);
    }

    @Override
    public void log(LogMeta logMeta) {
        SyncDocumentRequest syncDocumentRequest = new SyncDocumentRequest();
        rabbitTemplate.convertAndSend(EsConstant.QUEUE_SYNC_ES_DOCUMENT, syncDocumentRequest);
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}
