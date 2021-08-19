package com.architecture.es.consumer;


import com.architecture.thread.BaseTaskExecutor;


/**
 * @author luyi
 * es消费的线程池
 */
public class DocumentConsumerExecutor extends BaseTaskExecutor {

    private final static String ES_DOCUMENT_CONSUMER = "es_document_consumer";

    @Override
    public String getConfigId() {
        return ES_DOCUMENT_CONSUMER;
    }
}
