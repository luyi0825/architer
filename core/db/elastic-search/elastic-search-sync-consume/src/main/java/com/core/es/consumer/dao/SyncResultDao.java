package com.core.es.consumer.dao;

import com.core.es.consumer.entiry.SyncResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SyncResultDao extends MongoRepository<SyncResult, Long> {
    SyncResult findByBusinessKeyAndBatchId(String businessKey, String batchId);
    List<SyncResult> findByBatchIdAndBatchIdIn(String businessKey, String[] batchIds);

}
