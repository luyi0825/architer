package io.github.architers.context.sql;

/**
 * @author luyi
 * sql任务
 */
@FunctionalInterface
public interface SqlTask {


    /**
     * 执行任务
     */
    void execute();
}
