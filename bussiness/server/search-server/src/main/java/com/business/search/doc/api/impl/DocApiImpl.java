package com.business.search.doc.api.impl;

import com.business.search.doc.api.DocApi;
import com.business.search.doc.service.DocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * @author luyi
 */
@RestController
public class DocApiImpl implements DocApi {
    private DocService docService;

    @Override
    public void put(String index, Map<String, Object> docs) throws IOException {
        docService.put(index,docs);
    }

    @Override
    public Object queryForList(String index) throws IOException {
        return docService.queryList(index);
    }

    @Autowired
    public void setDataQueryService(DocService docService) {
        this.docService = docService;
    }
}
