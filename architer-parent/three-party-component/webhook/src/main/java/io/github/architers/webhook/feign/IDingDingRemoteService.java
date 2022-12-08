package io.github.architers.webhook.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "DingDing", url = "https://oapi.dingtalk.com", configuration =
        TestConfiguration.class)
public interface IDingDingRemoteService {
    @PostMapping(value = "/robot/send", headers = {"Content-Type=application/json;charset=UTF-8"})
    String send(@RequestParam("access_token") String accessToken,
                @RequestBody String message);

}
