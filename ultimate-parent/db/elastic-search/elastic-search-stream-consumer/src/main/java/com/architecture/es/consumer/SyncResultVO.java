package com.architecture.es.consumer;

import java.io.Serializable;


/**
 * 同步结果VO
 *
 * @author luyi
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
    private Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
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

    @Override
    public String toString() {
        return "SyncResultVO{" +
                "businessKey='" + businessKey + '\'' +
                ", batchId='" + batchId + '\'' +
                ", success=" + success +
                ", version=" + version +
                '}';
    }
}
