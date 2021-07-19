package com.core.es.dml.factory;


/**
 * @author luyi
 * 工厂支持，为满足适配转发的接口
 */
public interface FactorySupport<T> extends DocWriteRequestFactory<T> {
    /**
     * 是否支持
     *
     * @param requestType 请求类型
     * @return true为支持，支持则才执行后边的业务逻辑
     */
    boolean support(String requestType);
}
