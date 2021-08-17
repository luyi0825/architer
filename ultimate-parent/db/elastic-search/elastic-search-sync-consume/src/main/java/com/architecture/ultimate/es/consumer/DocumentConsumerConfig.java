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

}
