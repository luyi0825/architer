package com.business.base.codemap.api;

import cn.hutool.core.io.FileUtil;
import com.architecture.ultimate.module.common.response.ResponseResult;
import com.architecture.ultimate.utils.JsonUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.base.ModuleTest;
import com.business.base.codemap.api.codemap.CodeMapApiAdd;
import com.business.base.codemap.api.codemap.CodeMapApiUpdate;
import com.business.base.codemap.entity.CodeMap;
import com.business.base.codemap.service.CodeMapService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @see
 */
@SpringBootTest(classes = ModuleTest.class)
@AutoConfigureMockMvc
public class CodeMapApiTest {

    @Autowired
    private MockMvc mvc;

    @Value("classpath:CodeMap_test1.json")
    private Resource resource;

    public static final String requestMapping = "/codeMapApi";

    @Autowired
    private CodeMapApiAdd codeMapApiAdd;

    @Autowired
    private CodeMapApiUpdate codeMapApiUpdate;
    @Autowired
    private CodeMapService codeMapService;

    @Test
    void add() throws Exception {
        codeMapApiAdd.startTest();
    }

    @Test
    void update() throws Exception {
        codeMapApiUpdate.startTest();
    }


    @Test
    void findById() throws Exception {
        add();
        CodeMap codeMap = JsonUtils.readValue(FileUtil.readUtf8String(resource.getFile()), CodeMap.class);
        QueryWrapper<CodeMap> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", codeMap.getCode());
        codeMap = codeMapService.selectOne(queryWrapper);
        String str = mvc.perform(MockMvcRequestBuilders.get(requestMapping + "/get/" + codeMap.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ResponseResult responseResult = JsonUtils.readValue(str, ResponseResult.class);
        Assertions.assertEquals(200, (int) responseResult.getCode());
    }


    @Test
    void delete() throws Exception {
        add();
        CodeMap codeMap = JsonUtils.readValue(FileUtil.readUtf8String(resource.getFile()), CodeMap.class);
        QueryWrapper<CodeMap> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", codeMap.getCode());
        codeMap = codeMapService.selectOne(queryWrapper);
        String str = mvc.perform(MockMvcRequestBuilders.post(requestMapping + "/delete/" + codeMap.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ResponseResult responseResult = JsonUtils.readValue(str, ResponseResult.class);
        Assertions.assertEquals(200, (int) responseResult.getCode());
    }


}
