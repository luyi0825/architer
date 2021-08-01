package com.business.base.code.api;

import com.architecture.ultimate.module.common.StatusCode;
import com.architecture.ultimate.module.common.response.BaseResponse;
import com.architecture.ultimate.test.api.ApiHttpClient;
import com.business.base.code.entity.CodeConvert;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @see CodeConvertApi
 */
@SpringBootTest
@RunWith(value = SpringRunner.class)
public class CodeConvertApiTest {

    @Value("${api-host}")
    private String apiHost;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void add() throws IOException, InterruptedException {
        CodeConvert codeConvert = new CodeConvert();
        codeConvert.setCode("test-002");
        codeConvert.setCaption("测试");
        codeConvert.setRemark("测试");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiHost + "/codeConvertApi/add"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(codeConvert)))
                .build();
        HttpResponse<String> response = ApiHttpClient.build().send(request, HttpResponse.BodyHandlers.ofString());
        BaseResponse baseResponse = objectMapper.readValue(response.body(), BaseResponse.class);
        Assert.assertTrue(baseResponse.getCode() == 200 || baseResponse.getCode() == 100);
    }
}
