package com.architecture.es.consumer;



import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


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
