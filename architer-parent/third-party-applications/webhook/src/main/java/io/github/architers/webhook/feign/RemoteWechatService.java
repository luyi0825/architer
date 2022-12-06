package io.github.architers.webhook.feign;

import feign.Response;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 发送企业微信消息
 *
 * @author Winston
 * @date 2022/02/22
 */
@FeignClient(name = "remoteWechatService", configuration = RemoteWechatService.Configuration.class
)
public interface RemoteWechatService {

    class Configuration {
        @Bean
        Encoder feignFormPayEncoder(ObjectFactory<HttpMessageConverters> converters) {
            return new SpringFormEncoder(new SpringEncoder(converters));
        }
    }


    /**
     * 发消息
     *
     * @param key
     * @param msg
     * @return
     */
    @PostMapping(path = "/cgi-bin/webhook/send")
    Response sendWebHookMessage(@RequestParam("key") String key, @RequestBody String msg);


    /**
     * 发动应用消息
     *
     * @param accessToken token
     * @return
     */
    @PostMapping("cgi-bin/message/send")
    Response sendAppMessage(@RequestParam("access_token") String accessToken);




    @PostMapping("/https://oapi.dingtalk.com/robot/send")
    void send(@RequestParam("access_token") String accessToken,@RequestBody String message);


}

