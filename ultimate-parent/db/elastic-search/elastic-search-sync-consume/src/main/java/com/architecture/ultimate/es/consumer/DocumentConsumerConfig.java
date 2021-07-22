package com.architecture.ultimate.es.consumer;


import com.architecture.ultimate.thread.properties.TaskExecutorProperties;
import com.architecture.ultimate.thread.properties.ThreadPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author luyi
 * 文档消费配置类
 */
@Configuration
@ComponentScan("com.architecture.ultimate.es.consumer")
@EnableRabbit
@EnableMongoRepositories(basePackages = "com.architecture.ultimate.es.consumer.dao")
public class DocumentConsumerConfig {
    private final static String ES_DOCUMENT_CONSUMER = "es_document_consumer";
    private static final Logger LOG = LoggerFactory.getLogger(DocumentConsumerConfig.class);

    @Bean

    public DocumentConsumerExecutor documentConsumerExecutor(@Autowired(required = false) TaskExecutorProperties taskExecutorProperties) {
        ThreadPoolConfig threadPoolConfig = null;
        if (taskExecutorProperties != null) {
            Map<String, ThreadPoolConfig> threadPoolConfigMap = taskExecutorProperties.getConfigs();
            threadPoolConfig = threadPoolConfigMap.get(ES_DOCUMENT_CONSUMER);
        }
        if (threadPoolConfig == null) {
            LOG.error("注意:es消费数据线程池【{}】没有配置,采用默认配置", ES_DOCUMENT_CONSUMER);
            threadPoolConfig = new ThreadPoolConfig();
        }
        return new DocumentConsumerExecutor(threadPoolConfig);
    }
}
