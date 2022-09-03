package io.github.architers.context.thread.client;

/**
 * 拒绝策略：跟线程池拒绝策略一一对应
 *
 * @author luyi
 * @see java.util.concurrent.RejectedExecutionHandler 的实现类
 */
public enum RejectedHandler {
    /**
     * 由调用线程（提交任务的线程）处理该任务。
     */
    callerRunsPolicy(),

    /**
     * 抛出{@code RejectedExecutionException}的拒绝任务的处理程序，这个是默认的拒绝策略
     */
    abortPolicy(),
    /**
     * 拒绝任务的处理程序，静默丢弃被拒绝的任务,也就是不会抛出异常
     */
    discardPolicy(),

    /**
     * 拒绝任务的处理程序，丢弃最旧的未处理程序
     * 请求，然后重试{@code execute}，除非执行者
     * 关闭，在这种情况下该任务将被丢弃
     */
    discardOldestPolicy()
}
