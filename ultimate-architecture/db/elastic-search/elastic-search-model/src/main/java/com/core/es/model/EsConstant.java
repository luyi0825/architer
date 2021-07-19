package com.core.es.model;

/**
 * @author luyi
 * es对应的常量
 */
public class EsConstant {

    /**
     * 同步数据到es的交换机
     */
    public static final String EXCHANGE_SYNC_ES_DOCUMENT = "exchange_sync_es_document";
    /**
     * 同步document的常量
     * QUEUE_BATCH_SYNC_ES_DOCUMENT为批量同步
     * QUEUE_SYNC_ES_DOCUMENT为单个同步
     */
    public static final String QUEUE_BATCH_SYNC_ES_DOCUMENT = "queue_batch_sync_es_document";
    public static final String QUEUE_SYNC_ES_DOCUMENT = "queue_sync_es_document";

    /**
     * 回调参数的key
     *
     * @see com.core.es.model.dto.BaseSyncDocumentDTO#callBackWay
     */
    /**
     * 回调的url
     */
    public static final String CALL_BACK_URL = "call_back_url";
    /**
     * 回调的参数
     */
    public static final String CALL_BACK_PARAMS = "call_back_params";
}
