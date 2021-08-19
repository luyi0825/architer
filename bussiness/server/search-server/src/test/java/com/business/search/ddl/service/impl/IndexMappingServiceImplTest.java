package com.business.search.ddl.service.impl;


import com.architecture.es.dml.service.IndexMappingService;
import com.architecture.es.model.MappingType;
import com.architecture.es.model.ddl.IndexMapping;
import com.architecture.es.model.ddl.MappingItem;
import com.architecture.context.common.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class IndexMappingServiceImplTest {
    private final Logger logger = LoggerFactory.getLogger(IndexMappingServiceImplTest.class);

    @Autowired
    private IndexMappingService indexMappingService;


    @Test
    public void createIndexMapping() throws IOException {
        IndexMapping indexMapping = new IndexMapping();
        indexMapping.setIndex("test2");
        List<MappingItem> mappingItems = new ArrayList<>();
        MappingItem mappingItem = new MappingItem();
        mappingItem.setField("name");
        mappingItem.setMappingType(MappingType.KEYWORD);
        mappingItems.add(mappingItem);
        mappingItem = new MappingItem();
        mappingItem.setField("age");
        mappingItem.setMappingType(MappingType.LONG);
        mappingItems.add(mappingItem);
        mappingItem = new MappingItem();
        mappingItem.setField("address");
        mappingItem.setMappingType(MappingType.TEXT);
        mappingItems.add(mappingItem);
        mappingItem = new MappingItem();
        mappingItem.setField("money");
        mappingItem.setMappingType(MappingType.DOUBLE);
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
        mappingItem.setMappingType(MappingType.KEYWORD);
        mappingItems.add(mappingItem);
        mappingItem = new MappingItem();
        mappingItem.setField("age");
        mappingItem.setMappingType(MappingType.LONG);
        mappingItems.add(mappingItem);
        mappingItem = new MappingItem();
        mappingItem.setField("address");
        mappingItem.setMappingType(MappingType.TEXT);
        mappingItems.add(mappingItem);
        mappingItem = new MappingItem();
        mappingItem.setField("money");
        mappingItem.setMappingType(MappingType.DOUBLE);
        mappingItems.add(mappingItem);
        indexMapping.setMappingItems(mappingItems);
        indexMappingService.createIndexMapping(indexMapping);
        try {
            indexMappingService.rebuildIndexMapping(indexMapping);
        } catch (ServiceException serviceException) {
            logger.info(serviceException.getMessage(), serviceException);
        }

    }
}