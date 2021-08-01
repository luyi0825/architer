package com.business.base.area.api.impl;


import com.architecture.ultimate.module.common.StatusCode;
import com.architecture.ultimate.module.common.response.BaseResponse;
import com.architecture.ultimate.test.api.ApiHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@RunWith(value = SpringRunner.class)
@SpringBootTest
public class StandardAreaApiImplTest {

    @Value("${api-host}")
    private String apiHost;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void findByParentId() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiHost + "/standardAreaApi/findByParentId/0"))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = ApiHttpClient.build().send(request, HttpResponse.BodyHandlers.ofString());
        BaseResponse baseResponse = objectMapper.readValue(response.body(), BaseResponse.class);
        Assert.assertEquals(StatusCode.SUCCESS.getCode(), (int) baseResponse.getCode());
    }
}