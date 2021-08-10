package com.architecture.ultimate.starter.web;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ExceptionTestApiTest {

    @Autowired
    private MockMvc mockMvc;
    private final String API = "/exceptionTestApi";


    @Test
    void missingServletRequestParameterException() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(API + "/missingServletRequestParameterException").requestAttr("test", "54");
        String str = mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(str);
    }
}