package com.architecture.ultimate.es.elasticjob;

import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperConfiguration;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * @author luyi
 */
public class ElasticSearchJobConfig {


    public static void main(String[] args) {
        new ScheduleJobBootstrap(createRegistryCenter(), new CallBack(), createJobConfiguration()).schedule();
    }

    private static CoordinatorRegistryCenter createRegistryCenter() {
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("localhost:8080", "my-job"));
        regCenter.init();
        return regCenter;
    }


    public static JobConfiguration createJobConfiguration() {
        return JobConfiguration.newBuilder("MyJob", 3).cron("0/5 * * * * ?").build();
    }
}
