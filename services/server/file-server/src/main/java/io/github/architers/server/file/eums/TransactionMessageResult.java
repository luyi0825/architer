package io.github.architers.server.file.eums;

import io.github.architers.context.exception.BusLogException;
import lombok.Data;
import lombok.Getter;
import org.apache.rocketmq.client.producer.LocalTransactionState;

@Getter
public enum TransactionMessageResult {

    success(LocalTransactionState.COMMIT_MESSAGE, "成功"),
    failed(LocalTransactionState.ROLLBACK_MESSAGE, "失败"),
    unknown(LocalTransactionState.UNKNOW, "未知");

    private final LocalTransactionState transactionState;
    private final String caption;

    public static TransactionMessageResult of(LocalTransactionState localTransactionState) {
        for (TransactionMessageResult value : TransactionMessageResult.values()) {
            if (value.transactionState.equals(localTransactionState)) {
                return value;
            }
        }
        throw new BusLogException("localTransactionState有误");
    }


    TransactionMessageResult(LocalTransactionState transactionState, String caption) {
        this.transactionState = transactionState;
        this.caption = caption;
    }
}
