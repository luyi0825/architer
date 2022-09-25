package io.github.architers.test.task;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 本地事务执行器
 *
 * @author luyi
 */
@Service
public class LocalTransactionExecutor extends AbstractTaskExecutor implements CustomizeTaskExecutor {


    public LocalTransactionExecutor(TaskConsumerTargetRegister taskConsumerTargetRegister) {
        super(taskConsumerTargetRegister);
    }

    @Override
    public String getExecutorName() {
        return "localTransactionExecutor";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executor(SendParam request) {
        super.executor(request);
    }
}
