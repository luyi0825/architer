package io.github.architers.webhook;

import io.github.architers.context.webmvc.ResponseResult;

import java.io.Serializable;

/**
 * @author Administrator
 */
public interface IMessageRemote {

    /**
     * 得到机器人类型
     *
     * @return 机器人类型
     */
    String getRobotType();

    /**
     * 发送消息
     *
     * @param robotKey 自定义机器人的key
     * @param key      第三方定义的机器人key
     * @param message  需要发送的消息的字符串
     * @return 发送的结果
     */

    ResponseResult<? extends Serializable> sendMessage(String robotKey, String key, String message);

}
