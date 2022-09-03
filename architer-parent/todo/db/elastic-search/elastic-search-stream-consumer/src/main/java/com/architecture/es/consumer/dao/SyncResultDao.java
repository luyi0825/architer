package com.architecture.es.consumer.dao;


import com.architecture.es.consumer.entity.SyncResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author luyi
 * 同步结果dao层
 */
@Repository
public interface SyncResultDao extends MongoRepository<SyncResult, Long> {
    SyncResult findByBusinessKeyAndBatchId(String businessKey, String batchId);

    List<SyncResult> findByBusinessKeyAndBatchIdIn(String businessKey, Collection<String> batchId);
}
