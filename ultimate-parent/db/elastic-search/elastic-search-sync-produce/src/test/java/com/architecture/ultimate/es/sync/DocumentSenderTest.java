package com.architecture.ultimate.es.sync;

import cn.hutool.core.lang.UUID;
import com.architecture.ultimate.es.model.CallBackWay;
import com.architecture.ultimate.es.model.EsConstant;
import com.architecture.ultimate.es.model.RequestType;
import com.architecture.ultimate.es.model.doc.DocumentRequest;
import com.architecture.ultimate.es.model.dto.BatchSyncDocumentDTO;
import com.architecture.ultimate.es.model.dto.SyncDocumentDTO;
import com.architecture.ultimate.mq.rabbit.RabbitmqConstants;
import com.architecture.ultimate.mq.rabbit.callback.CallbackCorrelationData;
import com.architecture.ultimate.mq.rabbit.send.RabbitMqSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
public class DocumentSenderTest {

    private RabbitMqSender rabbitMqSender;
    private TaskExecutor taskExecutor;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void send() throws ExecutionException, InterruptedException {
        AtomicInteger id = new AtomicInteger(1);
        int num = 500;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            int finalI = i;
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                SyncDocumentDTO syncDocumentDTO = new SyncDocumentDTO();
                syncDocumentDTO.setBatchId(finalI + "");
                syncDocumentDTO.setBusinessKey("test");
                syncDocumentDTO.setCallBackWay(CallBackWay.URL.name());
                Map<String, Object> params = new HashMap<>();
                params.put(EsConstant.CALL_BACK_URL, "http://localhost:9008/callback");
                params.put(EsConstant.CALL_BACK_PARAMS, new HashMap<>());
                syncDocumentDTO.setCallBackParams(params);

                DocumentRequest documentRequest = new DocumentRequest();
                documentRequest.setId(id.getAndIncrement() + "");
                documentRequest.setIndex("test2");
                documentRequest.setRequestType(RequestType.INDEX.name());
                Map<String, Object> sources = new HashMap<>();
                sources.put("name", "name" + id);
                if (id.get() % 100 == 0) {
                    //每100，发送一条错误数据，模仿重试
                    // sources.put("age", "age");
                    sources.put("age", (int) (Math.random() * 100));
                } else {
                    sources.put("age", (int) (Math.random() * 100));
                }
                sources.put("address", "地址" + UUID.fastUUID());
                sources.put("money", Math.random() * 1000000);
                documentRequest.setSource(sources);
                syncDocumentDTO.setDoc(documentRequest);
                MessageProperties messageProperties = new MessageProperties();
                messageProperties.setHeader(RabbitmqConstants.MAX_RETRY_COUNT, 3);
                Message message = null;
                try {
                    message = new Message(objectMapper.writeValueAsBytes(syncDocumentDTO), messageProperties);
                    CallbackCorrelationData correlationData = new CallbackCorrelationData();
                    correlationData.setCallBackId(DocumentSendCallBack.class.getName());
                    correlationData.setId(finalI + "");
                    rabbitMqSender.send(EsConstant.EXCHANGE_SYNC_ES_DOCUMENT, EsConstant.QUEUE_SYNC_ES_DOCUMENT, message, correlationData);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } finally {

                }
            }, taskExecutor);
            completableFuture.get();
            countDownLatch.countDown();
        }
        countDownLatch.await();
        System.out.println(id.get());
        Thread.sleep(1000L * 30);
        // syncDocumentDTO.setDocs();


    }

    @Test
    public void batchSend() throws ExecutionException, InterruptedException {
        //00000
        AtomicInteger id = new AtomicInteger(1);
        int num = 10000;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            int finalI = i;
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                BatchSyncDocumentDTO syncDocumentDTO = new BatchSyncDocumentDTO();
                syncDocumentDTO.setBatchId(finalI + "");
                syncDocumentDTO.setBusinessKey("test");
                syncDocumentDTO.setCallBackWay(CallBackWay.URL.name());
                Map<String, Object> params = new HashMap<>();
                params.put(EsConstant.CALL_BACK_URL, "http://localhost:9008/callback");
                params.put(EsConstant.CALL_BACK_PARAMS, new HashMap<>());
                syncDocumentDTO.setCallBackParams(params);
                List<DocumentRequest> documentRequests = new ArrayList<>(10);
                for (int j = 0; j < 10; j++) {
                    DocumentRequest documentRequest = new DocumentRequest();
                    documentRequest.setId(id.getAndIncrement() + "");
                    documentRequest.setIndex("test2");
                    documentRequest.setRequestType(RequestType.INDEX.name());
                    Map<String, Object> sources = new HashMap<>();
                    sources.put("name", "name" + id);
                    if (id.get() % 100 == 0) {
                        //每100，发送一条错误数据，模仿重试
                        // sources.put("age", "age");
                        sources.put("age", (int) (Math.random() * 100));
                    } else {
                        sources.put("age", (int) (Math.random() * 100));
                    }
                    sources.put("address", "地址" + UUID.fastUUID());
                    sources.put("money", Math.random() * 1000000);
                    documentRequest.setSource(sources);
                    documentRequests.add(documentRequest);
                }
                syncDocumentDTO.setDocs(documentRequests);
                MessageProperties messageProperties = new MessageProperties();
                messageProperties.setHeader(RabbitmqConstants.MAX_RETRY_COUNT, 3);
                Message message = null;
                try {
                    message = new Message(objectMapper.writeValueAsBytes(syncDocumentDTO), messageProperties);
                    CallbackCorrelationData correlationData = new CallbackCorrelationData();
                    correlationData.setCallBackId(DocumentSendCallBack.class.getName());
                    correlationData.setId(finalI + "");
                    rabbitMqSender.send(EsConstant.EXCHANGE_SYNC_ES_DOCUMENT, EsConstant.QUEUE_BATCH_SYNC_ES_DOCUMENT, message, correlationData);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } finally {

                }
            }, taskExecutor);
            completableFuture.get();
            countDownLatch.countDown();
        }

        countDownLatch.await();
        System.out.println(id.get());
        Thread.sleep(1000L * 30);
        // syncDocumentDTO.setDocs();
    }

    @Autowired
    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @Autowired
    public void setRabbitMqSender(RabbitMqSender rabbitMqSender) {
        this.rabbitMqSender = rabbitMqSender;
    }
}