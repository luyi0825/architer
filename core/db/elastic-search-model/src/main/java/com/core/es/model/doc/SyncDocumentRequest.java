package com.core.es.model.doc;

/**
 * @author luyi
 * document的请求数据-同步数据用
 */
public class SyncDocumentRequest extends DocumentRequest {
    /**
     * 是否加锁，数据强一直性加锁处理--存在并发修改数据的时候才加
     * 会影响一定的效率
     */
    private boolean lock;
}
