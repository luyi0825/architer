package com.business.search.doc.service.impl;

import com.business.search.doc.service.DocService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @author luyi
 * 数据查询的service实现类
 */
@Service
public class DocServiceImpl implements DocService {

    private RestHighLevelClient restHighLevelClient;

    @Override
    public Object queryList(String index) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT).getHits();
    }

    @Override
    public void put(String index, Map<String, Object> docs) throws IOException {
        UpdateRequest request = new UpdateRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        request.doc(objectMapper.writeValueAsString(docs), XContentType.JSON);
        restHighLevelClient.update(request, RequestOptions.DEFAULT);
    }

    @Autowired
    public void setRestHighLevelClient(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }
}
