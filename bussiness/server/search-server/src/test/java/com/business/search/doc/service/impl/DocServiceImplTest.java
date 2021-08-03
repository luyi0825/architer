package com.business.search.doc.service.impl;


import com.architecture.ultimate.es.dml.service.DocService;
import com.architecture.ultimate.es.model.doc.DocumentRequest;
import com.architecture.ultimate.es.model.doc.DocumentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class DocServiceImplTest {
    private DocService docService;
    private final static String index = "test";
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        Map<String, Object> params = new HashMap<>();
        params.put("name", 6.1);
        params.put("age", "123");
        DocumentRequest documentRequest = new DocumentRequest();
        documentRequest.setId("123");
        documentRequest.setSource(params);
        System.out.println(objectMapper.writeValueAsString(documentRequest));
    }

    @Autowired
    public void setDocService(DocService docService) {
        this.docService = docService;
    }

    @Test
    public void insert() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("name", 6.1);
        params.put("age", "123");
        DocumentRequest documentRequest = new DocumentRequest();
        documentRequest.setId("123");
        documentRequest.setSource(params);
        System.out.println(objectMapper.writeValueAsString(documentRequest));
        for (int i = 0; i < 10; i++) {
            params.put("age", i);
            docService.bulkOne( documentRequest);
            //System.out.println(object);
        }
    }

    @Test
    public void findById() throws IOException {
        DocumentResponse documentResponse = docService.findById(index, "123");
        System.out.println(documentResponse);
    }

    @Test
    public void queryList() throws IOException {
        Object object = docService.queryList(index);
        System.out.println(objectMapper.writeValueAsString(object));
    }

    @Test
    public void update() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("name", 6.1);
        params.put("age", "123");
        DocumentRequest documentRequest = new DocumentRequest();
        documentRequest.setId("123");
        documentRequest.setSource(params);
        docService.bulkOne(documentRequest);
    }
}