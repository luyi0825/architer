package io.github.architers.webhook.wechat.remote;

import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import io.github.architers.context.utils.JsonUtils;
import io.github.architers.context.web.ResponseResult;
import io.github.architers.webhook.exeception.WebHookLimitException;
import io.github.architers.webhook.wechat.response.WechatResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.lang.reflect.Type;

@Configuration
public class ResultStatusDecoder implements Decoder {
    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {

        if (HttpStatus.OK.value() != response.status()) {
            return ResponseResult.fail(new String(Util.toByteArray(response.body().asInputStream())));
        }
        WechatResponse wechatResponse = JsonUtils.parseObject(Util.toByteArray(response.body().asInputStream()),
                        WechatResponse.class);
        if (wechatResponse.getErrcode() == 0) {
            return ResponseResult.ok(wechatResponse);
        }
        //被限流
        if (wechatResponse.getErrcode() == 45009) {
            throw new WebHookLimitException();
        }
        ResponseResult<WechatResponse> responseResult =
                ResponseResult.fail(wechatResponse.getErrmsg());
        responseResult.setResult(wechatResponse);
        return responseResult;
    }
}
