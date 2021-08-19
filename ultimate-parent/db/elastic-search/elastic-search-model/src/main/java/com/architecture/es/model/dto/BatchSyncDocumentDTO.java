package com.architecture.es.model.dto;

import com.architecture.es.model.doc.DocumentRequest;

import java.io.Serializable;
import java.util.List;

/**
 * @author luyi
 * 批量同步数据到es
 */
public class BatchSyncDocumentDTO extends BaseSyncDocumentDTO implements Serializable {


    private List<DocumentRequest> docs;

    public BatchSyncDocumentDTO() {

    }

    public List<DocumentRequest> getDocs() {
        return docs;
    }

    public void setDocs(List<DocumentRequest> docs) {
        this.docs = docs;
    }

    @Override
    public String toString() {
        return "BatchSyncDocumentDTO{" +
                "businessKey='" + businessKey + '\'' +
                ", batchId='" + batchId + '\'' +
                ", lock='" + lock + '\'' +
                ", version=" + version +
                ", callBackWay='" + callBackWay + '\'' +
                ", callBackParams=" + callBackParams +
                ", docs=" + docs +
                '}';
    }
}
