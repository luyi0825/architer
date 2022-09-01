package io.github.architers.es.dml.dao;


import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author luyi
 * es的mapping 操作
 */
@Component
public class IndexMappingDao {
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

    /**
     * 删除索引
     *
     * @param index 索引
     * @return 是否删除成功
     * @throws IOException es执行DeleteIndexRequest
     */
    public boolean deleteIndex(String... index) throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
        return client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT).isAcknowledged();
    }

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
