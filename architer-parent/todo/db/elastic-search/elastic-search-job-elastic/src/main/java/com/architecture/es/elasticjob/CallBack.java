package com.architecture.es.elasticjob;

import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.stereotype.Component;

/**
 * @author luyi
 */
@Component
public class CallBack implements SimpleJob {
    @Override
    public void execute(ShardingContext context) {
        System.out.println("in");
    }

}
