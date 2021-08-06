package com.business.base.codemap.api;

import com.architecture.ultimate.module.common.response.ResponseResult;
import com.architecture.ultimate.test.api.ApiHttpClient;
import com.business.base.codemap.entity.CodeMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @see CodeMapApi
 */
@SpringBootTest
public class CodeMapApiTest {

    @Value("${api-host}")
    private String apiHost;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void add() throws IOException, InterruptedException {
        CodeMap codeMap = new CodeMap();
        codeMap.setCode("test-002");
        codeMap.setCaption("测试");
        codeMap.setRemark("测试");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiHost + "/codeConvertApi/add"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(codeMap)))
                .build();
        HttpResponse<String> response = ApiHttpClient.build().send(request, HttpResponse.BodyHandlers.ofString());
        ResponseResult baseResponse = objectMapper.readValue(response.body(), ResponseResult.class);
        // Assert.assertTrue(baseResponse.getCode() == StatusCode.SUCCESS.getCode() || baseResponse.getCode() == 100);
    }
}
