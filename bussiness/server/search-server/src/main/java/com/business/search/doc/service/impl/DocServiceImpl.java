package com.business.search.doc.service.impl;


import com.business.search.doc.service.DocService;
import com.business.search.factory.RequestUtils;
import com.core.es.model.doc.DocumentRequest;
import com.core.es.model.doc.DocumentResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luyi
 * 数据查询的service实现类
 */
@Service
public class DocServiceImpl implements DocService {

    private RequestUtils requestUtils;

    private RestHighLevelClient restHighLevelClient;

    private static final String ID_KEY = "id";


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
    public void bulk(List<DocumentRequest> documentRequests) throws IOException {
        BulkRequest bulkRequest = requestUtils.getBulkRequest(documentRequests);
        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
    }

    @Override
    public void bulkOne(DocumentRequest documentRequest) throws IOException {
        BulkRequest bulkRequest = requestUtils.getBulkRequest(documentRequest);
        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
    }

    @Autowired
    public void setRestHighLevelClient(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Autowired
    public void setRequestUtils(RequestUtils requestUtils) {
        this.requestUtils = requestUtils;
    }
}
