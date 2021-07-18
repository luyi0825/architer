package com.core.es.consumer.service.impl;


import com.core.es.consumer.dao.SyncResultDao;
import com.core.es.consumer.entiry.SyncResult;
import com.core.es.consumer.service.SyncResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyncResultServiceImpl implements SyncResultService {
    private SyncResultDao syncResultDao;

    public void insert(SyncResult syncResult){
        syncResultDao.insert(syncResult);
    }

    public SyncResult findByBusinessKeyAndBatchId(String businessKey,String batchId){
        return syncResultDao.findByBusinessKeyAndBatchId(businessKey, batchId);
    }

    @Override
    public List<SyncResult> findByBusinessKeyAndBatchIds(String businessKey, String[] batchId) {
        return syncResultDao.findByBatchIdAndBatchIdIn(businessKey, batchId);
    }

    @Autowired
    public void setSyncResultDao(SyncResultDao syncResultDao) {
        this.syncResultDao = syncResultDao;
    }
}
