package com.architecture.ultimate.es.consumer.service.impl;


import com.architecture.ultimate.es.consumer.dao.SyncResultDao;
import com.architecture.ultimate.es.consumer.entity.SyncResult;
import com.architecture.ultimate.es.consumer.service.SyncResultService;
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

    @Override
    public void insert(SyncResult syncResult) {
        syncResultDao.insert(syncResult);
    }

    @Override
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
