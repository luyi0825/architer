package io.github.architers.webhook.dingding.remote;

import io.github.architers.context.webmvc.ResponseResult;
import io.github.architers.webhook.constants.RobotType;
import io.github.architers.webhook.dingding.DingDingResponse;
import io.github.architers.webhook.IMessageRemote;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "DingDing", url = "https://oapi.dingtalk.com", configuration =
        DingDingRemoteConfiguration.class)
public interface IDingDingRemoteService extends IMessageRemote {
    @PostMapping(value = "/robot/send", headers = {"Content-Type=application/json;charset=UTF-8"})
    @Override
    ResponseResult<DingDingResponse> sendMessage(@RequestParam("robotKey") String robotKey,
                                                 @RequestParam("access_token") String accessToken,
                                                 @RequestBody String message);

    @Override
    default String getRobotType() {
        return RobotType.DING_DING;
    }

}
