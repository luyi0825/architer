package com.business.base.codemap.service;

import cn.hutool.core.io.FileUtil;
import com.architecture.ultimate.utils.JsonUtils;
import com.business.base.ModuleTest;
import com.business.base.codemap.entity.CodeMap;
import com.business.base.codemap.entity.CodeMapItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = ModuleTest.class)
class CodeMapItemServiceTest {
    private final Logger logger = LoggerFactory.getLogger(CodeMapItemServiceTest.class);
    private CodeMapItemService codeMapItemService;
    @Value("classpath:CodeMapItem_test2.json")
    private Resource resource_02;

    private CodeMapServiceTest codeMapServiceTest;

    @Test
    public void insert() {
        codeMapServiceTest.insert();
        Assertions.assertThrows(DuplicateKeyException.class, () -> {
            insertCodeMapItem(resource_02);
            insertCodeMapItem(resource_02);
        });

    }

    private void insertCodeMapItem(Resource resource) throws IOException {
        String test = FileUtil.readUtf8String(resource.getFile());
        List<CodeMapItem> codeMapItems = JsonUtils.readListValue(test, CodeMapItem.class);
        codeMapItems.forEach(codeMapItem -> {
            int count = codeMapItemService.insert(codeMapItem);
            assertEquals(1, count);
        });
    }

    @Test
    void findByCode() {
        insert();
        List<CodeMapItem> list = codeMapItemService.findByCode("test2");
        logger.info(JsonUtils.toJsonString(list));
        assertFalse(CollectionUtils.isEmpty(list));
    }

    @Test
    void countByCode() {
        insert();
        int count = codeMapItemService.countByCode("test2");
        assertTrue(count > 0);
    }

    @Test
    void deleteByConvertCode() {
        codeMapItemService.deleteByCode("test2");
    }

    @Autowired
    public void setCodeMapItemService(CodeMapItemService codeMapItemService) {
        this.codeMapItemService = codeMapItemService;
    }

    @Autowired
    public void setCodeMapServiceTest(CodeMapServiceTest codeMapServiceTest) {
        this.codeMapServiceTest = codeMapServiceTest;
    }
}