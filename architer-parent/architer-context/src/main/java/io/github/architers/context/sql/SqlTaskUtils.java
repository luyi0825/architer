package io.github.architers.context.sql;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * sql任务执行器:<br>
 * <li>1.用于防止操作的sql过多，导致事务时间过长、连接持有时间过长（比如10个update需要执行，但是中间有很多查询的sql,而且这个sql很耗时）</li><br>
 * <li>2.解决一个事务中 业务逻辑1处理了很久，但是业务2参数就校验不通过，导致事务回滚导致的不足（资源浪费，数据库压力）</li>
 * <li>3.解决this调用事务的问题:虽然this可以引入自身代理或者使用exposeProxy解决，但是用起来比较麻烦，而且必须抽取公共方-用此类就可以直接在私有方法中使用事务</li>
 *
 * @author luyi
 */
public class SqlTaskUtils implements ApplicationContextAware {

    private static SqlTaskExecutor sqlTaskExecutor;


    public static void executorDelivery(DeliverySqlTask... sqlTaskList) {
        sqlTaskExecutor.executorDelivery(sqlTaskList);
    }


    public static void executor(SqlTask... sqlTasks) {
        sqlTaskExecutor.executor(sqlTasks);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        sqlTaskExecutor = applicationContext.getBean(SqlTaskExecutor.class);
    }
}
