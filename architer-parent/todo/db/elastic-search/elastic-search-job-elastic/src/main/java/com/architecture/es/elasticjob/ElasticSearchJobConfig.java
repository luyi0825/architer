package com.architecture.es.elasticjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author luyi
 */
@SpringBootApplication
public class ElasticSearchJobConfig {


    public static void main(String[] args) {
        new SpringApplication(ElasticSearchJobConfig.class).run(args);
    }

}
