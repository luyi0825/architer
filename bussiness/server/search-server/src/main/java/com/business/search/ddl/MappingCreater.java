package com.business.search.ddl;

import com.business.search.ddl.model.MappingItem;
import com.business.search.ddl.model.SearchMapping;
import com.core.module.common.exception.ParamsValidException;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luyi
 * 创造mapping
 */
@Component
public class MappingCreater {
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建mapping
     *
     * @param searchMapping
     * @return
     * @throws IOException
     */
    public CreateIndexResponse createMapping(SearchMapping searchMapping) throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(searchMapping.getIndex());
        List<MappingItem> mappingItems = searchMapping.getMappingItems();
        Map<String, String> mapping = new HashMap<>(mappingItems.size());
        mappingItems.forEach(mappingItem -> {
            String field = mappingItem.getField();
            if (StringUtils.isEmpty(field)) {
                throw new ParamsValidException("field is null");
            }
            MappingType mappingType = mappingItem.getMappingType();
            if (mappingType == null) {
                throw new ParamsValidException("MappingType is null");
            }
            mapping.put(field, mappingType.getType());
        });
        createIndexRequest.settings(mapping);
        return restHighLevelClient.indices().create(createIndexRequest, null);
    }
}
