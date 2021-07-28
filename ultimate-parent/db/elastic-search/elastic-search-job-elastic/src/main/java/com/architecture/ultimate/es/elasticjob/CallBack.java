package com.architecture.ultimate.es.elasticjob;

import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.dataflow.job.DataflowJob;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;

import java.util.List;

/**
 * @author luyi
 */
public class CallBack implements DataflowJob<String> {

    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        System.out.println(1);
        return null;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<String> list) {

    }
}
