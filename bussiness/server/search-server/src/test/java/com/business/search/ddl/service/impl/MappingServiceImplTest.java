package com.business.search.ddl.service.impl;

import com.business.search.ddl.MappingType;
import com.business.search.ddl.model.MappingItem;
import com.business.search.ddl.model.SearchMapping;
import com.business.search.ddl.service.MappingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(value= SpringRunner.class)
public class MappingServiceImplTest {
    @Autowired
    private MappingService mappingService;


    @Test
    public void createMapping() throws IOException {
        SearchMapping searchMapping = new SearchMapping();
        searchMapping.setIndex("test");
        List<MappingItem> mappingItems = new ArrayList<>();
        MappingItem mappingItem = new MappingItem();
        mappingItem.setField("name");
        mappingItem.setMappingType(MappingType.DOUBLE);
        mappingItems.add(mappingItem);
        searchMapping.setMappingItems(mappingItems);
        mappingService.createMapping(searchMapping);
    }
}