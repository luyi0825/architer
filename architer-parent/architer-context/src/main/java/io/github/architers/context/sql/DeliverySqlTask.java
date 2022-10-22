package io.github.architers.context.sql;

/**
 * @author luyi
 * sql任务
 */
@FunctionalInterface
public interface DeliverySqlTask {

    /**
     * 执行sql
     *
     * @param lastResult  上次的结果
     * @param last2Result 上上次的执行结果
     * @return 执行后的返回结果，可以用于给下步传参
     */
    Object execute(Object lastResult, Object last2Result);

}
