package io.github.architers.webhook.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.github.architers.webhook.WebhookProperties;
import lombok.SneakyThrows;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class TestConfiguration implements RequestInterceptor {

    @Resource
    private WebhookProperties webhookProperties;

    @SneakyThrows
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String secret = "SEC3acfe0dc18b4f8d7ce41616ff71969d6e9ab3a4bf6815cd52208ddb49065083b";
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
