package io.github.architers.es.model.dto;


import io.github.architers.es.model.doc.DocumentRequest;

import java.io.Serializable;

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

    @Override
    public String toString() {
        return "SyncDocumentDTO{" +
                "businessKey='" + businessKey + '\'' +
                ", batchId='" + batchId + '\'' +
                ", lock='" + lock + '\'' +
                ", version=" + version +
                ", callBackWay='" + callBackWay + '\'' +
                ", callBackParams=" + callBackParams +
                ", doc=" + doc +
                '}';
    }
}
