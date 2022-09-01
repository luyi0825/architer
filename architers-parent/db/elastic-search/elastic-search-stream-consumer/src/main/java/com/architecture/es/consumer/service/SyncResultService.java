package io.github.architers.es.consumer.service;


import io.github.architers.es.consumer.entity.SyncResult;

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
