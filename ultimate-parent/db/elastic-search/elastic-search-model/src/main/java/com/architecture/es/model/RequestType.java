package com.architecture.es.model;

/**
 * 请求类型
 *
 * @author luyi
 */
public enum RequestType {
    /**
     * 删除
     */
    DELETE(),
    /**
     * 更新
     */
    UPDATE(),

    /**
     * 存在的时候更新，不存在添加
     */
    INDEX();


}
