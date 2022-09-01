package elasticjob;

import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.dataflow.job.DataflowJob;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author luyi
 */
@Component
public class CallBack implements DataflowJob<String> {

    public CallBack(){
        System.out.println("CallBack init");
    }

    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        System.out.println("1");
        return null;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<String> list) {
        System.out.println(2);
    }
}
