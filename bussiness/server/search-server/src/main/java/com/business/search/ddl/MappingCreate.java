package com.business.search.ddl;

import com.business.search.ddl.model.MappingItem;
import com.business.search.ddl.model.SearchMapping;
import com.core.module.common.exception.ParamsValidException;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MappingCreate {
    private RestHighLevelClient client;







    @Autowired
    public void setRestHighLevelClient(RestHighLevelClient restHighLevelClient) {
        this.client = restHighLevelClient;
    }
}
