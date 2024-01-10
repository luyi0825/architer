package io.github.architers.context.sql;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

@Data
public class SqlTaskContext {

    /**
     * 事务任务
     */
    private List<SqlTask> transactionTasks;

    /**
     * 事务执行后的钩子
     */
    private List<SqlTask> endTransactionHooks;

    /**
     * 添加sql事务任务
     */
    public void addTransactionTask(SqlTask sqlTask) {
        if (transactionTasks == null) {
            transactionTasks = new ArrayList<>(4);
        }
        transactionTasks.add(sqlTask);
    }

    public void addEndTransactionHook(SqlTask sqlTask) {
        if (endTransactionHooks == null) {
            endTransactionHooks = new ArrayList<>(3);
        }
        endTransactionHooks.add(sqlTask);
    }
}
