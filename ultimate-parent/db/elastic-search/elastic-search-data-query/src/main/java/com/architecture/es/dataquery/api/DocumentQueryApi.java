package com.architecture.es.dataquery.api;

import com.architecture.es.model.doc.DocumentResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author admin
 */
@RestController
public class DocumentQueryApi {

    private DocumentQueryService documentQueryService;

    @RequestMapping("/{index}/{documentId}")
    public DocumentResponse findById(@PathVariable(name = "index") String index, @PathVariable(name = "documentId") String documentId) throws IOException {
        return documentQueryService.findById(index, documentId);
    }
}
