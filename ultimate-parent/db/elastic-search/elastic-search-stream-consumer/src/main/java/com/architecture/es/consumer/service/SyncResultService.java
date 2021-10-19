package com.architecture.es.consumer.service;


import com.architecture.es.consumer.entity.SyncResult;

import java.util.List;

/**
 * @author luyi
 * 同步结果对应的service接口层
 */
public interface SyncResultService {
    void insert(SyncResult syncResult);

    SyncResult findByBusinessKeyAndBatchId(String businessKey, String batchId);

    List<SyncResult> findByBusinessKeyAndBatchIds(String businessKey, String[] batchId);

}
