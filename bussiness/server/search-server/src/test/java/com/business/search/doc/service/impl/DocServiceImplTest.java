package com.business.search.doc.service.impl;

import com.business.search.doc.model.DocumentRequest;
import com.business.search.doc.model.DocumentResponse;
import com.business.search.doc.service.DocService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(value = SpringRunner.class)
public class DocServiceImplTest {
    private DocService docService;
    private final static String index = "test";
    private final ObjectMapper objectMapper = new ObjectMapper();


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
        for (int i = 0; i < 10; i++) {
            params.put("age", i);
            Object object = docService.insert(index, documentRequest);
            System.out.println(object);
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
        docService.update(index, documentRequest);
    }
}