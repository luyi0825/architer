package io.github.architers.es.consumer;



import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


/**
 * @author luyi
 * 文档消费配置类
 */
@Configuration
@ComponentScan("io.github.architers.es.consumer")
@EnableMongoRepositories(basePackages = "io.github.architers.es.consumer.dao")
public class DocumentConsumerConfig {

}
