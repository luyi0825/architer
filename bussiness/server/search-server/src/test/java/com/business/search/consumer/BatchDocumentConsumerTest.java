package com.business.search.consumer;

import cn.hutool.core.lang.UUID;

import com.architecture.ultimate.es.model.EsConstant;
import com.architecture.ultimate.es.model.RequestType;
import com.architecture.ultimate.es.model.doc.DocumentRequest;
import com.architecture.ultimate.es.model.dto.BatchSyncDocumentDTO;
import com.architecture.ultimate.mq.rabbit.RabbitmqConstants;
import com.architecture.ultimate.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@RunWith(value = SpringRunner.class)
public class BatchDocumentConsumerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        BatchSyncDocumentDTO syncDocumentDTO = new BatchSyncDocumentDTO();
        List<DocumentRequest> documentRequests = new ArrayList<>(10);
        for (int j = 0; j < 10; j++) {
            DocumentRequest documentRequest = new DocumentRequest();
            documentRequest.setId(j + "");
            documentRequest.setIndex("test");
            documentRequest.setRequestType(RequestType.INDEX.name());
            Map<String, Object> sources = new HashMap<>();
            sources.put("name", "name" + j);
            sources.put("age", (int) (Math.random() * 100));
            sources.put("address", "地址" + UUID.fastUUID());
            sources.put("money", Math.random() * 1000000);
            documentRequest.setSource(sources);
            documentRequests.add(documentRequest);
        }
        syncDocumentDTO.setDocs(documentRequests);
        String str = JsonUtils.toJsonString(syncDocumentDTO);
        syncDocumentDTO = JsonUtils.readValue(str, syncDocumentDTO.getClass());
        System.out.println(syncDocumentDTO);
    }

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Test
    public void batchSendDocument() {

        AtomicInteger id = new AtomicInteger(1);
        for (int i = 0; i < 100000; i++) {
            int finalI = i;
            executorService.submit(() -> {
                BatchSyncDocumentDTO syncDocumentDTO = new BatchSyncDocumentDTO();
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
                        sources.put("age", "age");
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
                    CorrelationData correlationData = new CorrelationData();
                    correlationData.setId(finalI + "");
                    rabbitTemplate.send(EsConstant.EXCHANGE_SYNC_ES_DOCUMENT, EsConstant.QUEUE_BATCH_SYNC_ES_DOCUMENT, message, correlationData);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            });
        }
        // syncDocumentDTO.setDocs();
    }


}