package com.business.search.ddl;

import com.business.search.ddl.model.MappingItem;
import com.business.search.ddl.model.SearchMapping;
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
public class MappingCreateTest {

    @Autowired
    private MappingCreate mappingCreate;


    @Test
    public void createMapping() throws IOException {
        SearchMapping searchMapping = new SearchMapping();
        searchMapping.setIndex("test");
        List<MappingItem> mappingItems = new ArrayList<>();
        MappingItem mappingItem = new MappingItem();
        mappingItem.setField("name");
        mappingItem.setMappingType(MappingType.KEYWORD);
        mappingItems.add(mappingItem);
        searchMapping.setMappingItems(mappingItems);
        mappingCreate.createMapping(searchMapping);
    }
}