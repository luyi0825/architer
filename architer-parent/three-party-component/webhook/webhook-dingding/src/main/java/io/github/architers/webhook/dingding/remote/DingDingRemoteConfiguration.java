package io.github.architers.webhook.dingding.remote;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.github.architers.context.exception.BusLogException;
import io.github.architers.webhook.WebhookProperties;
import lombok.SneakyThrows;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
public class DingDingRemoteConfiguration implements RequestInterceptor {

    @Resource
    private WebhookProperties webhookProperties;

    @SneakyThrows
    @Override
    public void apply(RequestTemplate requestTemplate) {
        Collection<String> robotKeys = requestTemplate.queries().get("robotKey");
        if (CollectionUtils.isEmpty(robotKeys)) {
            throw new BusLogException("robotKey is null");
        }
        String secret = webhookProperties.getRobotConfigs().get(robotKeys.toArray(new String[0])[0]).getSecret();
        long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), StandardCharsets.UTF_8);
        System.out.println(sign);
        requestTemplate.query("timestamp", List.of(timestamp + ""));
        requestTemplate.query("sign", Collections.singleton(sign));
    }
}
