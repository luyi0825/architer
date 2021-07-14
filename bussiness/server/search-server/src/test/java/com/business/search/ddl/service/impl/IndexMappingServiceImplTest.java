package com.business.search.ddl.service.impl;

import com.business.search.ddl.MappingType;
import com.business.search.ddl.model.MappingItem;
import com.business.search.ddl.model.IndexMapping;
import com.business.search.ddl.service.IndexMappingService;
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
        mappingItem.setField("_id");
        mappingItem.setMappingType(MappingType.LONG);
        mappingItems.add(mappingItem);
        indexMapping.setMappingItems(mappingItems);
        indexMappingService.createIndexMapping(indexMapping);
    }

    @Test
    public void rebuildIndexMapping() throws IOException {
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
        indexMappingService.rebuildIndexMapping(indexMapping);
    }
}