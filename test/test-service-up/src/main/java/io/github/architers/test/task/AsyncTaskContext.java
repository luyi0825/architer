package io.github.architers.test.task;

public class AsyncTaskContext {

    private static ThreadLocal<Boolean> executors = new ThreadLocal<>();

    /**
     * @return
     */
    public static boolean isExecutor() {
        return Boolean.TRUE.equals(executors.get());
    }

    /**
     * 执行中
     */
    public static void startExecutor() {
        executors.set(true);
    }

    /**
     * 执行结束
     */
    public static void endExecutor() {
        executors.remove();
    }


}
