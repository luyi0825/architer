package com.core.es.consumer;

import com.lz.thread.properties.TaskExecutorProperties;
import com.lz.thread.properties.ThreadPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;

import java.util.Map;

public class DocumentConsumerConfig {
    private final static String es_document_consumer = "es_document_consumer";
    private static final Logger LOG = LoggerFactory.getLogger(DocumentConsumerConfig.class);

    @Bean
    public DocumentConsumerExecutor DocumentConsumerExecutor(TaskExecutorProperties taskExecutorProperties) {
        Map<String, ThreadPoolConfig> threadPoolConfigMap = taskExecutorProperties.getConfigs();
        ThreadPoolConfig threadPoolConfig = null;
        if (!CollectionUtils.isEmpty(threadPoolConfigMap)) {
            threadPoolConfig = threadPoolConfigMap.get(es_document_consumer);
        }
        if (threadPoolConfig == null) {
            LOG.error("注意:es消费数据线程池【{}】没有配置,采用默认配置", es_document_consumer);
            threadPoolConfig = new ThreadPoolConfig();
        }
        return new DocumentConsumerExecutor(threadPoolConfig);
    }
}
