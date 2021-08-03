package com.business.base.area.api.impl;


import com.architecture.ultimate.module.common.StatusCode;
import com.architecture.ultimate.module.common.response.BaseResponse;
import com.architecture.ultimate.test.api.ApiHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;


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
        assertThat(StatusCode.SUCCESS.getCode()).isEqualTo(baseResponse.getCode());
    }
}