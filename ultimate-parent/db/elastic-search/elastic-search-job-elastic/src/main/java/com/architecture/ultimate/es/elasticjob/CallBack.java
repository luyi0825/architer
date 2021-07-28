package com.architecture.ultimate.es.elasticjob;

import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
/**
 * @author luyi
 */
public class CallBack implements SimpleJob {
    @Override
    public void execute(ShardingContext context) {
        System.out.println("in");
    }

}
