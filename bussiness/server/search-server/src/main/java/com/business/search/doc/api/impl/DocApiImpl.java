package com.business.search.doc.api.impl;

import com.business.search.doc.api.DocApi;
import com.business.search.doc.model.DocumentRequest;
import com.business.search.doc.service.DocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


/**
 * @author luyi
 */
@RestController
public class DocApiImpl implements DocApi {
    private DocService docService;

    @Override
    public void put(String index, DocumentRequest documentRequest) throws IOException {
        docService.update(index, documentRequest);
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
