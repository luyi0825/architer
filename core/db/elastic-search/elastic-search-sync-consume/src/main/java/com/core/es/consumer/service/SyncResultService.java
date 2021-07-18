package com.core.es.consumer.service;


import com.core.es.consumer.entiry.SyncResult;

import java.util.List;

public interface SyncResultService {
    void insert(SyncResult syncResult);

    SyncResult findByBusinessKeyAndBatchId(String businessKey, String batchId);

    List<SyncResult> findByBusinessKeyAndBatchIds(String businessKey, String[] batchId);

}
