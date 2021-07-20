package com.architecture.ultimate.es.consumer.api;



import com.architecture.ultimate.es.consumer.entity.SyncResult;
import com.architecture.ultimate.es.consumer.service.SyncResultService;
import com.architecture.ultimate.es.consumer.vo.SyncResultVO;
import org.springframework.beans.factory.annotation.Autowired;
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
        return this.getSyncResultVo(syncResult);
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

    /**
     * 得到同步结果的VO
     *
     * @param syncResult 同步结果
     */
    private SyncResultVO getSyncResultVo(SyncResult syncResult) {
        if (syncResult == null) {
            return null;
        }
        SyncResultVO syncResultVO = new SyncResultVO();
        syncResultVO.setBusinessKey(syncResult.getBusinessKey());
        syncResultVO.setBatchId(syncResult.getBatchId());
        syncResultVO.setSuccess(syncResult.isSuccess());
        syncResultVO.setVersion(syncResult.getVersion());
        return syncResultVO;
    }

    @Autowired
    public void setSyncResultService(SyncResultService syncResultService) {
        this.syncResultService = syncResultService;
    }
}
