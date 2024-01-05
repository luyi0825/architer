package io.github.architers.webhook.wechat.remote;

import io.github.architers.context.webmvc.ResponseResult;
import io.github.architers.webhook.constants.RobotType;
import io.github.architers.webhook.IMessageRemote;
import io.github.architers.webhook.wechat.response.WechatResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 发送企业微信消息
 *
 * @author luyi
 */
@FeignClient(name = "remoteWechatService", url = "https://qyapi.weixin.qq.com/"
        , configuration = ResultStatusDecoder.class)
public interface RemoteWechatService extends IMessageRemote {


    @Override
    default String getRobotType() {
        return RobotType.WECHAT;
    }


    /**
     * 发消息
     *
     * @param robotKey 自定义机器人的key
     * @param key
     * @param msg
     * @return
     */
    @PostMapping(path = "/cgi-bin/webhook/send")
    ResponseResult<WechatResponse> sendMessage(@RequestParam("robotKey") String robotKey,
                                               @RequestParam("key") String key,
                                               @RequestBody String msg);


}

