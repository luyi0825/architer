package com.core.es.model.dto;

import com.core.es.model.doc.DocumentRequest;

import java.io.Serializable;
import java.util.List;

/**
 * @author luyi
 * document的请求数据-同步数据用
 */
public class SyncDocumentDTO extends BaseSyncDocumentDTO implements Serializable {



    private DocumentRequest doc;

    public SyncDocumentDTO() {

    }

    public DocumentRequest getDoc() {
        return doc;
    }

    public void setDoc(DocumentRequest doc) {
        this.doc = doc;
    }
}
