package com.business.search.consumer;

import com.business.search.doc.service.DocService;
import com.core.es.model.EsConstant;
import com.core.es.model.doc.DocumentRequest;
import com.core.es.model.doc.SyncDocumentDTO;
import com.core.mq.rabbit.RetryUtils;
import com.core.mq.rabbit.RetryType;
import com.core.utils.JsonUtils;
import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author luyi
 * es文档消费者
 */
@Component
@Log4j2
public class DocumentConsumer {
    private DocService docService;

    @RabbitListener(queuesToDeclare = {@Queue(name = EsConstant.QUEUE_SYNC_ES_DOCUMENT)})
    public void handler(Message message, Channel channel) throws IOException {
        try {
            List<SyncDocumentDTO> syncDocumentDTOList = JsonUtils.readListValue(message.getBody(), SyncDocumentDTO.class);
            for (SyncDocumentDTO syncDocumentDTO : syncDocumentDTOList) {
                List<DocumentRequest> documentRequests = syncDocumentDTO.getDocs();
                docService.bulk(documentRequests);
            }
            //手动签收
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (Exception e) {
            log.error("处理消息异常", e);
            //异常重回对列
            RetryUtils.exceptionRetry(message, channel, RetryType.REQUEUE);
        }
    }

    @Autowired
    public void setDocService(DocService docService) {
        this.docService = docService;
    }

}
