package io.github.architers.context.sql;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * sql任务执行器:<br>
 * <li>1.用于防止操作的sql过多，导致事务时间过长、连接持有时间过长（比如10个update需要执行，但是中间有很多查询的sql,而且这个sql很耗时）</li><br>
 * <li>2.解决一个事务中 业务逻辑1处理了很久，但是业务2参数就校验不通过，导致事务回滚导致的不足（资源浪费，数据库压力）</li>
 * <li>3.解决this调用事务的问题:虽然this可以引入自身代理或者使用exposeProxy解决，但是用起来比较麻烦，而且必须抽取公共方-用此类就可以直接在私有方法中使用事务</li>
 *
 * @author luyi
 */
@Component
public class SqlTaskExecutor {

    /**
     * 执行sql任务（事务传播行为为：Propagation.REQUIRED）
     *
     * @param sqlTasks sql执行任务，不能为空
     */
    @Transactional(rollbackFor = Exception.class)
    public void executorRequired(SqlTask... sqlTasks) {
        for (SqlTask sqlTask : sqlTasks) {
            sqlTask.execute();
        }
    }

    /**
     * 执行sql任务（事务传播行为为：Propagation.REQUIRES_NEW）
     *
     * @param sqlTasks sql执行任务，不能为空
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void executorRequiredNew(SqlTask... sqlTasks) {
        for (SqlTask sqlTask : sqlTasks) {
            sqlTask.execute();
        }
    }
}
