package com.business.search.ddl.dao;


import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author luyi
 * es的mapping 操作
 */
@Component
public class MappingDao {
    private RestHighLevelClient client;

    /**
     * 添加mapping
     *
     * @param index   对应es中的index
     * @param mapping mapping 定义
     * @return 是否成功
     * @throws IOException client.indices().putMapping 抛出的异常
     */
    public boolean putMapping(String index, Map<String, ?> mapping) throws IOException {
        PutMappingRequest putMappingRequest = new PutMappingRequest(index);
        putMappingRequest.source(mapping);
        return client.indices().putMapping(putMappingRequest, RequestOptions.DEFAULT).isAcknowledged();
    }


    @Autowired
    public void setClient(RestHighLevelClient client) {
        this.client = client;
    }
}
