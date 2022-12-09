package io.github.architers.webhook;

import io.github.architers.context.web.ResponseResult;

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

    ResponseResult<? extends Serializable> sendMessage(String key, String message);

}
