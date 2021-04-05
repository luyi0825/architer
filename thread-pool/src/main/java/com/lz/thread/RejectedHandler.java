package com.lz.thread;

/**
 * 描述：拒绝策略
 * 跟线程池对应
 *
 * @author luyi
 * @TODO 其他的拒绝策略还没有完善
 * @date 2021/3/16
 */
public enum RejectedHandler {

    /**
     * 抛出{@code RejectedExecutionException}的拒绝任务的处理程序
     */
    abortPolicy(),
    /**
     * 拒绝任务的处理程序，静默丢弃被拒绝的任务,也就是不会抛出异常
     */
    discardPolicy()

}
