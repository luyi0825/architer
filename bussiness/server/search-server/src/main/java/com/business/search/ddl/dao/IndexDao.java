package com.business.search.ddl.dao;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author luyi
 * index操作
 */
@Component
public class IndexDao {
    private RestHighLevelClient client;

    /**
     * 判断index是否存在
     *
     * @param index index值
     * @return 存在返回true
     * @throws IOException IOException
     */
    public boolean exists(String index) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(index);
        return client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
    }

    public boolean createIndex(String index, Map<String, ?> settings, Map<String, ?> mappings) throws IOException {
        CreateIndexRequest indexRequest = new CreateIndexRequest(index);
        if (!CollectionUtils.isEmpty(settings)) {
            indexRequest.settings(settings);
        }
        if (!CollectionUtils.isEmpty(mappings)) {
            indexRequest.mapping(mappings);
        }
        return client.indices().create(indexRequest, RequestOptions.DEFAULT).isAcknowledged();
    }

    @Autowired
    public void setClient(RestHighLevelClient client) {
        this.client = client;
    }
}
