package com.architecture.ultimate.thread;


/**
 * @author luyi
 * 公共的线程池：
 * 1.为了解决系统线程池过多的问题
 * 2.可以根据配置修改线程池，不必改代码，增加灵活度
 * 3.可以处理一些异步任务,用户不需要立即收到任务处理结果的任务，比如系统的定时任务,系统预警发送邮箱,日志、实施人员的导入导出等等
 * 注意：
 * 1.例如一些查询优化（采用多线程加快查询速度），需要及时响应需要用到线程池的地方，请自定义线程池（防止线程池的队列太长，影响核心业务、用户体验等）
 */
public class CommonTaskExecutor extends BaseTaskExecutor {

    private final static String CONFIG_ID = "common";

    public CommonTaskExecutor() {
        super();
    }

    @Override
    public String getConfigId() {
        return CONFIG_ID;
    }
}
