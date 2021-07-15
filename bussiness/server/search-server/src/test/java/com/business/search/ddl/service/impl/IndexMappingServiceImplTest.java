package com.business.search.ddl.service.impl;


import com.business.search.ddl.service.IndexMappingService;
import com.core.es.model.MappingType;
import com.core.es.model.ddl.IndexMapping;
import com.core.es.model.ddl.MappingItem;
import com.core.module.common.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(value = SpringRunner.class)
@Log4j2
public class IndexMappingServiceImplTest {
    @Autowired
    private IndexMappingService indexMappingService;


    @Test
    public void createIndexMapping() throws IOException {
        IndexMapping indexMapping = new IndexMapping();
        indexMapping.setIndex("test2");
        List<MappingItem> mappingItems = new ArrayList<>();
        MappingItem mappingItem = new MappingItem();
        mappingItem.setField("name");
        mappingItem.setMappingType(MappingType.DOUBLE);
        mappingItems.add(mappingItem);
        mappingItem = new MappingItem();
        mappingItem.setField("age");
        mappingItem.setMappingType(MappingType.LONG);
        mappingItems.add(mappingItem);
        indexMapping.setMappingItems(mappingItems);
        indexMappingService.createIndexMapping(indexMapping);
    }

    @Test
    public void rebuildIndexMapping() throws IOException {
        IndexMapping indexMapping = new IndexMapping();
        indexMapping.setIndex("test");
        List<MappingItem> mappingItems = new ArrayList<>();
        MappingItem mappingItem = new MappingItem();
        mappingItem.setField("name");
        mappingItem.setMappingType(MappingType.DOUBLE);
        mappingItems.add(mappingItem);
        mappingItem = new MappingItem();
        mappingItem.setField("id");
        mappingItem.setMappingType(MappingType.LONG);
        mappingItems.add(mappingItem);
        indexMapping.setMappingItems(mappingItems);
        try {
            indexMappingService.rebuildIndexMapping(indexMapping);
        } catch (ServiceException serviceException) {
            log.info(serviceException.getMessage(), serviceException);
        }

    }
}