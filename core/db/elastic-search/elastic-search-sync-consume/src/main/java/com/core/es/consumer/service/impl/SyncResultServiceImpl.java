package com.core.es.consumer.service.impl;


import com.core.es.consumer.dao.SyncResultDao;
import com.core.es.consumer.entiry.SyncResult;
import com.core.es.consumer.service.SyncResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author luyi
 * 同步结果对应的service实现层
 */
@Service
public class SyncResultServiceImpl implements SyncResultService {
    private SyncResultDao syncResultDao;

    public void insert(SyncResult syncResult) {
        syncResultDao.insert(syncResult);
    }

    public SyncResult findByBusinessKeyAndBatchId(String businessKey, String batchId) {
        return syncResultDao.findByBusinessKeyAndBatchId(businessKey, batchId);
    }

    @Override
    public List<SyncResult> findByBusinessKeyAndBatchIds(String businessKey, String[] batchIds) {
        return syncResultDao.findByBusinessKeyAndBatchIdIn(businessKey, Arrays.asList(batchIds));
    }

    @Autowired
    public void setSyncResultDao(SyncResultDao syncResultDao) {
        this.syncResultDao = syncResultDao;
    }
}
