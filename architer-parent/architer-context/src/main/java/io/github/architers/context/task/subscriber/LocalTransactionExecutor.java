package io.github.architers.context.task.subscriber;

import io.github.architers.context.task.TaskStore;
import io.github.architers.context.task.constants.ExecutorName;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 本地事务执行器
 *
 * @author luyi
 */
@Service
public class LocalTransactionExecutor extends AbstractTaskExecutor implements CustomizeTaskExecutor {


    public LocalTransactionExecutor(TaskSubscriberTargetScanner taskSubscriberTargetScanner) {
        super(taskSubscriberTargetScanner);
    }

    @Override
    public String getExecutorName() {
        return ExecutorName.LOCAL_TRANSACTION;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executor(TaskStore taskStore) {
        super.executor(taskStore);
    }
}
