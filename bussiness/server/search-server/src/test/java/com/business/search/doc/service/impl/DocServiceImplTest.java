package com.business.search.doc.service.impl;

import com.business.search.doc.service.DocService;
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

    @Test
    public void putTest() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("name", 6.1);
        params.put("id","123");
        docService.put(index, params);
    }

    @Autowired
    public void setDocService(DocService docService) {
        this.docService = docService;
    }
}