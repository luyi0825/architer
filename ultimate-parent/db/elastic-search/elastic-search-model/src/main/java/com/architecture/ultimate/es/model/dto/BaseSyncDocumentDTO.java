package com.architecture.ultimate.es.model.dto;


import java.io.Serializable;
import java.util.Map;


/**
 * @author luyi
 * 同步数据到ES的DTO基类
 */
public abstract class BaseSyncDocumentDTO implements Serializable {
    /**
     * 同步的业务标识key，没有业务标识的数据将不提供，由ES服务统一提供
     * 没有的数据不处理，后期可以同步这个businessKey和batchId查询同步的结果
     */
    private String businessKey;
    /**
     * 同步的批次ID
     */
    private String batchId;
    /**
     * 是否加锁，数据强一直性加锁处理--存在并发修改数据的时候才加
     * 会影响一定的效率,不为空的时候会加锁
     */
    private String lock;
    /**
     * 版本，用于控制顺序,当加锁的时候，高版本的doc操作后，将放弃低版本的数据
     */
    private Long version;
    /**
     * 回调方式
     * 同步成功后，自动回调
     * 1.如果有回调的url,将自动回调，然后post的方式回调
     * 2.队列的方式回调，发送回调数据到制定的消息队列
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

    public Map<String, Object> getCallBackParams() {
        return callBackParams;
    }

    public void setCallBackParams(Map<String, Object> callBackParams) {
        this.callBackParams = callBackParams;
    }

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }


}
