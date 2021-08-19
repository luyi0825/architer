package com.architecture.test.api;


import java.net.http.HttpClient;
import java.time.Duration;

/**
 * @author luyi
 * 构建api测试的httpClient
 */
public class ApiHttpClient {
    public static HttpClient build() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(6))
                .build();
    }
}
