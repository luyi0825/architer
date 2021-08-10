package com.architecture.ultimate.starter.web.validexception;


import com.architecture.ultimate.module.common.ResponseStatusEnum;
import com.architecture.ultimate.module.common.response.ResponseResult;
import com.architecture.ultimate.starter.web.exception.GlobalExceptionHandler;
import com.architecture.ultimate.utils.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.MessageFormat;

@SpringBootTest
@AutoConfigureMockMvc
class ExceptionTestApiTest {

    @Autowired
    private MockMvc mockMvc;
    private static final String API = "/exceptionTestApi";

    @Test
    void missingServletRequestParameterException() throws Exception {
        //没有参数的校验
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(API + "/missingServletRequestParameterException");//.param("test", "a54");
        String str = mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        ResponseResult responseResult = JsonUtils.readValue(str, ResponseResult.class);
        Assertions.assertEquals(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(), (int) responseResult.getCode());
        Assertions.assertEquals(MessageFormat.format(GlobalExceptionHandler.MISSING_PARAMETER_EXCEPTION_TIP, "test"), responseResult.getMessage());
        //有参数
        builder = MockMvcRequestBuilders.get(API + "/missingServletRequestParameterException").param("test", "123");
        str = mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        responseResult = JsonUtils.readValue(str, ResponseResult.class);
        Assertions.assertEquals(ResponseStatusEnum.SUCCESS.getCode(), (int) responseResult.getCode());
    }
}