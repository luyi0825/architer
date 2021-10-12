package com.architecture.es.consumer.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Document(collection = "t_document_sync_result")
public class SyncResult implements Serializable {
    /**
     * 主键ID
     */
    @Id
    private String id;
    /**
     * 业务key
     */
    private String businessKey;
    /**
     * 批次号
     */
    private String batchId;
    /**
     * 版本
     */
    private Long version;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否回调成功
     */
    private boolean isCallback = false;
    /**
     *
     */
    private boolean isSuccess;
    /**
     * 回调way
     */
    private String callBackWay;
    /**
     * 回调参数
     */
    private Map<String, Object> callBackParams;

    public String getCallBackWay() {
        return callBackWay;
    }

    public void setCallBackWay(String callBackWay) {
        this.callBackWay = callBackWay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public boolean isCallback() {
        return isCallback;
    }

    public void setCallback(boolean callback) {
        isCallback = callback;
    }

    public Map<String, Object> getCallBackParams() {
        return callBackParams;
    }

    public void setCallBackParams(Map<String, Object> callBackParams) {
        this.callBackParams = callBackParams;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
