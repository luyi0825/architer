package com.business.base.codemap.service;

import cn.hutool.core.io.FileUtil;
import com.architecture.ultimate.module.common.exception.ParamsValidException;
import com.architecture.ultimate.utils.JsonUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.base.ModuleTest;
import com.business.base.codemap.entity.CodeMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = ModuleTest.class)
@Component
public class CodeMapServiceTest {

    private CodeMapService codeMapService;

    @Value("classpath:CodeMap_test1.json")
    private Resource codeMap_test1;

    @Value("classpath:CodeMap_test2.json")
    private Resource codeMap_test2;


    @Test
    public void insert() {
        Throwable throwable1 = assertThrows(ParamsValidException.class, () -> {
            insertCodeMap(codeMap_test1);
            insertCodeMap(codeMap_test1);
        });
        assertEquals("编码【test1】已经存在", throwable1.getMessage());
        Throwable throwable2 = assertThrows(ParamsValidException.class, () -> {
            insertCodeMap(codeMap_test2);
            insertCodeMap(codeMap_test2);
        });
        assertEquals("编码【test2】已经存在", throwable2.getMessage());
    }

    private void insertCodeMap(Resource resource) throws IOException {
        String test = FileUtil.readUtf8String(resource.getFile());
        CodeMap codeMap = JsonUtils.readValue(test, CodeMap.class);
        int count = codeMapService.insert(codeMap);
        assertEquals(1, count);
    }

    @Test
    void delete() {
        //测试正常删除
        QueryWrapper<CodeMap> codeMapQueryWrapper = new QueryWrapper<>();
        codeMapQueryWrapper.eq("code", "test1");
        CodeMap codeMap = codeMapService.selectOne(codeMapQueryWrapper);
        int count = codeMapService.delete(codeMap.getId());
        assertEquals(1, count);

        codeMapQueryWrapper = new QueryWrapper<>();
        codeMapQueryWrapper.eq("code", "test2");
        codeMap = codeMapService.selectOne(codeMapQueryWrapper);
        count = codeMapService.delete(codeMap.getId());
        assertEquals(1, count);
    }

    @Test
    void setCodeMapItemService() {
    }

    @Autowired
    public void setCodeMapService(CodeMapService codeMapService) {
        this.codeMapService = codeMapService;
    }

}