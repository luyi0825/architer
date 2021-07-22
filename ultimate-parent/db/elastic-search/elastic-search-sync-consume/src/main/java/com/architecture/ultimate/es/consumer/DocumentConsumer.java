package com.architecture.ultimate.es.consumer;


import com.architecture.ultimate.es.consumer.entity.SyncResult;
import com.architecture.ultimate.es.consumer.service.SyncResultService;
import com.architecture.ultimate.es.dml.service.DocService;
import com.architecture.ultimate.es.model.EsConstant;
import com.architecture.ultimate.es.model.dto.BaseSyncDocumentDTO;
import com.architecture.ultimate.es.model.dto.BatchSyncDocumentDTO;
import com.architecture.ultimate.es.model.dto.SyncDocumentDTO;
import com.architecture.ultimate.module.common.exception.ParamsValidException;
import com.architecture.ultimate.module.common.exception.ServiceException;
import com.architecture.ultimate.mq.rabbit.RetryType;
import com.architecture.ultimate.mq.rabbit.RetryUtils;

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
import java.util.Date;
import java.util.concurrent.CompletableFuture;

/**
 * @author luyi
 * es文档消费者-单条数据
 */
@Component
public class DocumentConsumer {
    private DocService docService;
    private final Logger log = LoggerFactory.getLogger(DocumentConsumer.class);
    private RetryUtils retryUtils;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private DocumentConsumerExecutor executor;
    private CallBackHandler callBackHandler;
    private SyncResultService syncResultService;

    /**
     * 监听es数据同步的队列
     * 绑定交换机
     *
     * @param message 消息数据
     * @param channel 管道数据
     * @throws IOException mq签收抛出的异常
     */
    @RabbitListener(bindings = {@QueueBinding(exchange = @Exchange(name = EsConstant.EXCHANGE_SYNC_ES_DOCUMENT),
            value = @Queue(name = EsConstant.QUEUE_SYNC_ES_DOCUMENT), key = EsConstant.QUEUE_SYNC_ES_DOCUMENT)})
    public void handler(Message message, Channel channel) throws IOException {
        SyncDocumentDTO syncDocumentDTO = null;
        try {
            syncDocumentDTO = objectMapper.readValue(message.getBody(), SyncDocumentDTO.class);
        } catch (Exception e) {
            //防止数据解析异常，导致的异常重试
            log.error("解析数据失败，格式有误");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
        //解析成功了，再执行业务
        SyncDocumentDTO finalSyncDocumentDTO = syncDocumentDTO;
        this.syncDocumentHandler(syncDocumentDTO,
                baseSyncDocumentDTO -> docService.bulkOne(finalSyncDocumentDTO.getDoc()), message, channel);

    }

    /**
     * 监听es批量数据同步的队列
     * 绑定交换机
     *
     * @param message 消息数据
     * @param channel 管道数据
     * @throws IOException mq签收抛出的异常
     */
    @RabbitListener(bindings = {@QueueBinding(exchange = @Exchange(name = EsConstant.EXCHANGE_SYNC_ES_DOCUMENT),
            key = {EsConstant.QUEUE_BATCH_SYNC_ES_DOCUMENT}, value = @Queue(name = EsConstant.QUEUE_BATCH_SYNC_ES_DOCUMENT))})
    public void batchHandler(Message message, Channel channel) throws IOException {
        BatchSyncDocumentDTO batchSyncDocumentDTO = null;
        try {
            batchSyncDocumentDTO = objectMapper.readValue(message.getBody(), BatchSyncDocumentDTO.class);
        } catch (Exception e) {
            //防止数据解析异常，导致的异常重试
            log.error("解析数据失败，格式有误");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
        //解析成功了，再执行业务
        BatchSyncDocumentDTO finalBatchSyncDocumentDTO = batchSyncDocumentDTO;
        this.syncDocumentHandler(batchSyncDocumentDTO,
                test -> docService.bulk(finalBatchSyncDocumentDTO.getDocs()), message, channel);
    }


    /**
     * 同步doc处理
     */
    public void syncDocumentHandler(BaseSyncDocumentDTO baseSyncDocumentDTO,
                                    SyncFunction function,
                                    Message message,
                                    Channel channel) throws IOException {
        if (baseSyncDocumentDTO == null) {
            return;
        }
        try {
            CompletableFuture.runAsync(() -> {
                try {
                    function.handler(baseSyncDocumentDTO);
                } catch (IOException e) {
                    //异常转换
                    throw new RuntimeException("es数据处理失败");
                }
                this.saveSyncResult(baseSyncDocumentDTO, true);
            }, executor).exceptionally(throwable -> {
                log.error("同步失败", throwable);
                this.saveSyncResult(baseSyncDocumentDTO, false);
                return null;
            });
            //手动签收
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (ServiceException | IllegalArgumentException | ParamsValidException e) {
            //业务 校验失败，数据有问题，则不需要在重试
            log.info(e.getMessage(), e);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("处理消息异常", e);
            //根据业务重试，或者丢失
            retryUtils.exceptionRetry(message, channel, RetryType.REQUEUE);
        }
    }

    /**
     * 保存同步结果
     *
     * @param baseSyncDocumentDTO 同步的数据
     * @param isSuccess           是否同步成功
     */
    private void saveSyncResult(BaseSyncDocumentDTO baseSyncDocumentDTO, boolean isSuccess) {
        SyncResult syncResult = new SyncResult();
        syncResult.setBusinessKey(baseSyncDocumentDTO.getBusinessKey());
        syncResult.setBatchId(baseSyncDocumentDTO.getBatchId());
        syncResult.setSuccess(isSuccess);
        syncResult.setCreateTime(new Date());
        syncResult.setVersion(baseSyncDocumentDTO.getVersion());
        syncResult.setCallBackWay(baseSyncDocumentDTO.getCallBackWay());
        syncResult.setCallBackParams(baseSyncDocumentDTO.getCallBackParams());
        syncResultService.insert(syncResult);
        callBackHandler.callBack(baseSyncDocumentDTO, syncResult);
    }


    @Autowired(required = false)
    public void setDocService(DocService docService) {
        this.docService = docService;
    }

    @Autowired(required = false)
    public void setRetryUtils(RetryUtils retryUtils) {
        this.retryUtils = retryUtils;
    }

    @Autowired
    public void setExecutor(DocumentConsumerExecutor executor) {
        this.executor = executor;
    }

    @Autowired
    public void setCallBackHandler(CallBackHandler callBackHandler) {
        this.callBackHandler = callBackHandler;
    }

    @Autowired
    public void setSyncResultService(SyncResultService syncResultService) {
        this.syncResultService = syncResultService;
    }
}
