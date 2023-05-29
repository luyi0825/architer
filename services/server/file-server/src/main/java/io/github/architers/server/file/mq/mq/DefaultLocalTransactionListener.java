package io.github.architers.server.file.mq.mq;

import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 默认的事务监听器
 *
 * @author luyi
 */
@RocketMQTransactionListener
@Slf4j
public class DefaultLocalTransactionListener implements RocketMQLocalTransactionListener {


    private List<LocalTransactionExecute> localTransactionExecutes;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {

        if (CollectionUtils.isEmpty(localTransactionExecutes)) {
            //没有执行器回滚事务
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        String businessKey = TransactionMessageUtils.getBusinessKey(msg);
        if (StringUtil.isBlank(businessKey)) {
            log.error("事务消息不合法：businessKey为空");
            //回滚事务
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        try {
            for (LocalTransactionExecute localTransactionExecute : localTransactionExecutes) {
                if (businessKey.equals(localTransactionExecute.getBusinessKey())) {
                    return localTransactionExecute.executeLocalTransaction(msg, arg);
                }
            }
        } catch (Exception e) {
            log.error("执行本地事务失败", e);
            //回滚事务
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        log.error("事务消息不合法：businessKey为空");
        //回滚事务
        return RocketMQLocalTransactionState.ROLLBACK;


    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        if (CollectionUtils.isEmpty(localTransactionExecutes)) {
            //没有执行器回滚事务
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        String businessKey = TransactionMessageUtils.getBusinessKey(msg);
        if (StringUtil.isBlank(businessKey)) {
            log.error("事务消息不合法：businessKey为空");
            //回滚事务
            return RocketMQLocalTransactionState.ROLLBACK;
        }

        for (LocalTransactionExecute localTransactionExecute : localTransactionExecutes) {
            if (businessKey.equals(localTransactionExecute.getBusinessKey())) {
                return localTransactionExecute.checkLocalTransaction(msg);
            }
        }
        log.error("事务消息不合法：businessKey为空");
        //回滚事务
        return RocketMQLocalTransactionState.ROLLBACK;

    }


    @Autowired(required = false)
    public void setLocalTransactionExecutes(List<LocalTransactionExecute> localTransactionExecutes) {
        this.localTransactionExecutes = localTransactionExecutes;
    }
}
