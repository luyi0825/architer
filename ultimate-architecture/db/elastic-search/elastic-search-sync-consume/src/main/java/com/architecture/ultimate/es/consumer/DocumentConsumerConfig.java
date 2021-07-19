package com.core.es.consumer;

import com.core.thread.properties.TaskExecutorProperties;
import com.core.thread.properties.ThreadPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author luyi
 * 文档消费配置类
 */
@Configuration
@ComponentScan("com.core.es.consumer")
public class DocumentConsumerConfig {
    private final static String ES_DOCUMENT_CONSUMER = "es_document_consumer";
    private static final Logger LOG = LoggerFactory.getLogger(DocumentConsumerConfig.class);

    @Bean
    public DocumentConsumerExecutor documentConsumerExecutor(@Autowired(required = false) TaskExecutorProperties taskExecutorProperties) {
        Map<String, ThreadPoolConfig> threadPoolConfigMap = taskExecutorProperties.getConfigs();
        ThreadPoolConfig threadPoolConfig = null;
        if (!CollectionUtils.isEmpty(threadPoolConfigMap)) {
            threadPoolConfig = threadPoolConfigMap.get(ES_DOCUMENT_CONSUMER);
        }
        if (threadPoolConfig == null) {
            LOG.error("注意:es消费数据线程池【{}】没有配置,采用默认配置", ES_DOCUMENT_CONSUMER);
            threadPoolConfig = new ThreadPoolConfig();
        }
        return new DocumentConsumerExecutor(threadPoolConfig);
    }
}
