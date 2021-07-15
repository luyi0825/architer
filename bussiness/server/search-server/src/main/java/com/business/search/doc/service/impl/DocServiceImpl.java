package com.business.search.doc.service.impl;


import com.business.search.doc.service.DocService;
import com.core.es.model.doc.DocumentRequest;
import com.core.es.model.doc.DocumentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luyi
 * 数据查询的service实现类
 */
@Service
public class DocServiceImpl implements DocService {

    private RestHighLevelClient restHighLevelClient;

    private static final String ID_KEY = "id";

    @Override
    public DocumentRequest insert(String index, DocumentRequest docs) throws IOException {
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index(index);
        if (StringUtils.hasText(docs.getId())) {
            indexRequest.id(docs.getId());
        }
        indexRequest.source(docs.getSource());
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse.getResult().getOp());
        docs.setId(indexResponse.getId());
        return docs;
    }

    @Override
    public DocumentResponse findById(String index, String id) throws IOException {
        GetRequest getRequest = new GetRequest(index, id);
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        DocumentResponse documentResponse = new DocumentResponse(getResponse.getSource());
        documentResponse.put(ID_KEY, getResponse.getId());
        return documentResponse;
    }

    @Override
    public List<DocumentResponse> queryList(String index) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        if (searchHits != null) {
            List<DocumentResponse> documents = new ArrayList<>(searchHits.length);
            for (SearchHit hit : searchHits) {
                DocumentResponse documentResponse = new DocumentResponse(hit.getSourceAsMap());
                documentResponse.put(ID_KEY, hit.getId());
                documents.add(documentResponse);
            }
            return documents;
        }
        return null;
    }

    @Override
    public void update(String index, DocumentRequest documentRequest) throws IOException {
        UpdateRequest request = new UpdateRequest(index, documentRequest.getId());
        ObjectMapper objectMapper = new ObjectMapper();
        request.doc(objectMapper.writeValueAsString(documentRequest), XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(updateResponse.getResult().getOp());
    }

    @Autowired
    public void setRestHighLevelClient(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }
}
