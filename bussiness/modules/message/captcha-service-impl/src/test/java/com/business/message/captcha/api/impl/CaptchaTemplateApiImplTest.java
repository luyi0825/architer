package com.business.message.captcha.api.impl;

import com.business.message.captcha.entity.CaptchaTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest
@RunWith(value = SpringRunner.class)
class CaptchaTemplateApiImplTest {
    private String url = "localhost:8080";


    @Autowired
    private RestTemplate restTemplate;


    @Test
    void pageQuery() {

    }

    @Test
    void addCaptchaTemplate() {
        Map<String, String> params = new HashMap<>();
        restTemplate.postForObject(url + "/captchaTemplateApi/add", params, CaptchaTemplate.class);

    }

    @org.junit.jupiter.api.Test
    void findById() {
    }

    @org.junit.jupiter.api.Test
    void updateCaptchaTemplate() {
    }
}