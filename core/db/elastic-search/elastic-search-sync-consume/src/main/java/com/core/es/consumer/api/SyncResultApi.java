package com.core.es.consumer.api;

import com.core.es.consumer.entiry.SyncResult;
import com.core.es.consumer.service.SyncResultService;
import com.core.es.consumer.vo.SyncResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/syncResult")
public class SyncResultApi {

    private SyncResultService syncResultService;

    @GetMapping("/{businessKey}/{batchId}")
    public SyncResultVO getSyncResult(@PathVariable(name = "businessKey") String businessKey, @PathVariable(name = "batchId") String batchId) {
        SyncResult syncResult = syncResultService.findByBusinessKeyAndBatchId(businessKey, batchId);
        SyncResultVO syncResultVO = new SyncResultVO();
        syncResultVO.setBusinessKey(syncResult.getBusinessKey());
        syncResultVO.setBatchId(syncResult.getBatchId());
        syncResultVO.setCallback(syncResult.isCallback());
        return syncResultVO;
    }


    @GetMapping("/{businessKey}")
    public List<SyncResultVO> getSyncResults(@PathVariable(name = "businessKey") String businessKey, @RequestBody String[] batchIds) {
        List<SyncResult> syncResults = syncResultService.findByBusinessKeyAndBatchIds(businessKey, batchIds);
        if (syncResults != null) {
            List<SyncResultVO> syncResultVOS = new ArrayList<>(syncResults.size());
            for (SyncResult syncResult : syncResults) {
                syncResultVOS.add(getSyncResultVo(syncResult));
            }
            return syncResultVOS;
        }
        return new ArrayList<>(0);
    }

    private SyncResultVO getSyncResultVo(SyncResult syncResult) {
        SyncResultVO syncResultVO = new SyncResultVO();
        syncResultVO.setBusinessKey(syncResult.getBusinessKey());
        syncResultVO.setBatchId(syncResult.getBatchId());
        syncResultVO.setCallback(syncResult.isCallback());
        syncResultVO.setVersion(syncResult.getVersion());
        return syncResultVO;
    }

    @Autowired
    public void setSyncResultService(SyncResultService syncResultService) {
        this.syncResultService = syncResultService;
    }
}
