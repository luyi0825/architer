package com.business.base.codemap.api;

import cn.hutool.core.io.FileUtil;
import com.architecture.ultimate.module.common.response.ResponseResult;
import com.architecture.ultimate.utils.JsonUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.base.ModuleTest;
import com.business.base.codemap.api.codemapitem.CodeMapItemAddTest;
import com.business.base.codemap.api.codemapitem.CodeMapItemUpdateTest;
import com.business.base.codemap.entity.CodeMap;
import com.business.base.codemap.entity.CodeMapItem;
import com.business.base.codemap.service.CodeMapItemService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
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
    @Autowired
    private CodeMapItemUpdateTest codeMapItemUpdateTest;
    @Autowired
    private CodeMapItemService codeMapItemService;
    @Value("classpath:CodeMapItem_test2.json")
    private Resource resource;
    public static final String apiRequestMapping = "/codeMapItemApi";

    @Test
    void add() throws Exception {
        codeMapItemAddTest.startTest();
    }

    @Test
    void update() throws Exception {
        codeMapItemUpdateTest.startTest();
    }

    @Test
    void findByCode() throws Exception {
        add();
        String returnStr = mockMvc.perform(MockMvcRequestBuilders.get(apiRequestMapping + "/findByCode/test2")).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ResponseResult responseResult = JsonUtils.readValue(returnStr, ResponseResult.class);
        assertEquals(200, responseResult.getCode());

        List<CodeMapItem> list = JsonUtils.readListValue(JsonUtils.toJsonString(responseResult.getData()), CodeMapItem.class);
        logger.info("findByCode:{}", JsonUtils.toJsonString(list));


    }


    @Test
    void getById() throws Exception {
        add();
        String codeMapItemStr = FileUtil.readUtf8String(resource.getFile());
        List<CodeMapItem> codeMapItems = JsonUtils.readListValue(codeMapItemStr, CodeMapItem.class);
        CodeMapItem codeMapItem = codeMapItems.get(0);
        QueryWrapper<CodeMapItem> codeMapItemQueryWrapper = new QueryWrapper<>();
        codeMapItemQueryWrapper.eq("code", codeMapItem.getCode());
        codeMapItemQueryWrapper.eq("item_code", codeMapItem.getItemCode());
        codeMapItem = codeMapItemService.selectOne(codeMapItemQueryWrapper);
        String returnStr = mockMvc.perform(MockMvcRequestBuilders.get(apiRequestMapping + "/getById/" + codeMapItem.getId())).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ResponseResult responseResult = JsonUtils.readValue(returnStr, ResponseResult.class);
        assertEquals(200, responseResult.getCode());
        assertNotNull(responseResult.getData());
        logger.info(JsonUtils.toJsonString(responseResult.getData()));

    }
}