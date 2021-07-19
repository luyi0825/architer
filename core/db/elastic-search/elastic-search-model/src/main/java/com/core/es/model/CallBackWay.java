package com.core.es.model;

/**
 * 请求成功后回调的方式
 *
 * @author luyi
 */
public enum CallBackWay {
    /**
     * 通过http的地址
     */
    URL(),
    /**
     * 通过队列的方式
     */
    QUEUE(),
    /**
     * 不需要回调
     */
    NONE();
}
