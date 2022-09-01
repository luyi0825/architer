package elasticjob;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author luyi
 */
@SpringBootApplication
public class ElasticSearchJobConfig {


    public static void main(String[] args){
        //EmbedZookeeperServer.start(6181);
        new SpringApplication(ElasticSearchJobConfig.class).run(args);
    }
}
