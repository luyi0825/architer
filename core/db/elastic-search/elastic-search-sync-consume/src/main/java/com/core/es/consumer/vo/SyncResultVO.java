package com.core.es.consumer.vo;

import java.io.Serializable;


/**
 * 同步结果VO
 */
public class SyncResultVO implements Serializable {
    /**
     * 业务key
     */
    private String businessKey;
    /**
     * 批次号
     */
    private String batchId;
    /**
     * 是否回调成功
     */
    private boolean isSuccess;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    private Long version;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

}
