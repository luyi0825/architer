package io.github.architers.server.file.mq.mq;

import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

/**
 * 本地事务执行器
 */
public interface LocalTransactionExecute {

    /**
     * 得到业务类型
     *
     * @return
     */
    String getBusinessKey();

    /**
     * 执行本地事务
     */
    RocketMQLocalTransactionState executeLocalTransaction(Message<?> msg, Object arg);

    /**
     * 检查本地事务
     */
    RocketMQLocalTransactionState checkLocalTransaction(Message<?> msg);

}
