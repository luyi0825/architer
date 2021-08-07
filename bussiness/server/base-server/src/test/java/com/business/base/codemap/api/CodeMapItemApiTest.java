package com.business.base.codemap.api;

import com.architecture.ultimate.module.common.response.ResponseResult;
import com.architecture.ultimate.utils.JsonUtils;
import com.business.base.ModuleTest;
import com.business.base.codemap.api.codemapitem.CodeMapItemAddTest;
import com.business.base.codemap.entity.CodeMapItem;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ModuleTest.class)
@AutoConfigureMockMvc
public
class CodeMapItemApiTest {
    private final Logger logger = LoggerFactory.getLogger(CodeMapItemApiTest.class);
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CodeMapItemAddTest codeMapItemAddTest;

    public static final String apiRequestMapping = "/codeMapItemApi";

    @Test
    void add() throws Exception {
        codeMapItemAddTest.startTest();
    }

    @Test
    void findByCode() throws Exception {
        String returnStr = mockMvc.perform(MockMvcRequestBuilders.get(apiRequestMapping + "/findByCode/test2")).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ResponseResult responseResult = JsonUtils.readValue(returnStr, ResponseResult.class);
        assertEquals(200, responseResult.getCode());
        if (responseResult.getData() != null) {
            List<CodeMapItem> list = JsonUtils.readListValue(JsonUtils.toJsonString(responseResult.getData()), CodeMapItem.class);
            logger.info("findByCode:{}", JsonUtils.toJsonString(list));
        }

    }


    @Test
    void update() {
    }

    @Test
    void getById() {
    }
}