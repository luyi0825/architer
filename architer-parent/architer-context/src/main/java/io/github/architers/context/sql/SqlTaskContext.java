package io.github.architers.context.sql;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

@Data
public class SqlTaskContext {




    private List<SqlTask> sqlTaskList;

    /**
     * 事务执行后的钩子
     */
    private Supplier<Runnable> endTransactionHook;


    public void addSqlTask(SqlTask sqlTask) {
        if (sqlTaskList == null) {
            sqlTaskList = new LinkedList<>();
        }
        sqlTaskList.add(sqlTask);
    }
}
