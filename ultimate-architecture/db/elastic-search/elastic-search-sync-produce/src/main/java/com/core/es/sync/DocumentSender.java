package com.core.es.sync;

import com.core.es.model.EsConstant;
import com.core.es.model.dto.BatchSyncDocumentDTO;
import com.core.es.model.dto.SyncDocumentDTO;
import com.core.utils.JsonUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author luyi
 * 文档发送器
 * 生产者校验数据通过后才发送到中间件，从而一定减少消费端的校验->这样可以让消费的更快，消费的都是有效的数据
 */
@Component
public class DocumentSender {
    private RabbitTemplate rabbitTemplate;

    /**
     * 单个发送
     *
     * @param syncDocumentDTO   同步单个数据的dto
     * @param messageProperties mq的消息属性
     * @param correlationData   mq关联数据信息
     */
    public void send(SyncDocumentDTO syncDocumentDTO, MessageProperties messageProperties, CorrelationData correlationData) {
        SenderValidUtils.validSyncDocumentDTO(syncDocumentDTO);
        Message message = new Message(JsonUtils.writeValueAsBytes(syncDocumentDTO), messageProperties);
        rabbitTemplate.send(EsConstant.EXCHANGE_SYNC_ES_DOCUMENT, EsConstant.QUEUE_SYNC_ES_DOCUMENT, message, correlationData);
    }

    /**
     * 批量发送
     *
     * @param batchSyncDocumentDTO 批量发送到es数据的dto
     * @param messageProperties    mq的消息属性
     * @param correlationData      mq关联数据信息
     */
    public void batchSend(BatchSyncDocumentDTO batchSyncDocumentDTO, MessageProperties messageProperties, CorrelationData correlationData) {
        SenderValidUtils.validBatchSyncDocumentDTO(batchSyncDocumentDTO);
        Message message = new Message(JsonUtils.writeValueAsBytes(batchSyncDocumentDTO), messageProperties);
        rabbitTemplate.send(EsConstant.EXCHANGE_SYNC_ES_DOCUMENT, EsConstant.QUEUE_BATCH_SYNC_ES_DOCUMENT, message, correlationData);
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}
