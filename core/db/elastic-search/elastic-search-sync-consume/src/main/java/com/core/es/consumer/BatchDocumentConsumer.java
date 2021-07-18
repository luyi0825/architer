package com.core.es.consumer;


import com.core.es.dml.service.DocService;
import com.core.es.model.EsConstant;
import com.core.es.model.doc.DocumentRequest;
import com.core.es.model.dto.BatchSyncDocumentDTO;
import com.core.mq.rabbit.RetryUtils;
import com.core.mq.rabbit.RetryType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.List;

/**
 * @author luyi
 * es文档消费者-批量数据
 */
@Component
public class BatchDocumentConsumer {
    private DocService docService;
    private final Logger log = LoggerFactory.getLogger(BatchDocumentConsumer.class);
    private RetryUtils retryUtils;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private CallBackHandler callBackHandler;


    @Autowired
    public void setDocService(DocService docService) {
        this.docService = docService;
    }

    @Autowired
    public void setRetryUtils(RetryUtils retryUtils) {
        this.retryUtils = retryUtils;
    }

    @Autowired
    public void setCallBackHandler(CallBackHandler callBackHandler) {
        this.callBackHandler = callBackHandler;
    }
}
