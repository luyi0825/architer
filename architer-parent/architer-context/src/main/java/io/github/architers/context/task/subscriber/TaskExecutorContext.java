package io.github.architers.context.task.subscriber;

/**
 * 任务执行器context
 *
 * @author luyi
 */
public class TaskExecutorContext {

    private static final ThreadLocal<Boolean> EXECUTORS = new ThreadLocal<>();

    /**
     * @return
     */
    public static boolean isExecutor() {
        return Boolean.TRUE.equals(EXECUTORS.get());
    }

    /**
     * 执行中
     */
    public static void startExecutor() {
        EXECUTORS.set(true);
    }

    /**
     * 执行结束
     */
    public static void endExecutor() {
        EXECUTORS.remove();
    }


}
