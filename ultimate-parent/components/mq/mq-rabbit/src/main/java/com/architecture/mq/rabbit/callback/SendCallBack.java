package com.architecture.mq.rabbit.callback;

/**
 *
 */
public interface SendCallBack {
    /**
     * 得到处理的key,
     * 这个key不允许重复，一个实现类对应一个，标识区分
     *
     * @return 处理器标识key
     */
    String getCallBackId();

}
