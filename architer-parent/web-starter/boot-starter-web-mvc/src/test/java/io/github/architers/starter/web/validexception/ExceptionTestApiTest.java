package io.github.architers.starter.web.validexception;



import io.github.architers.context.utils.JsonUtils;
import io.github.architers.context.web.ResponseResult;
import io.github.architers.starter.web.response.ResponseStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ExceptionTestApiTest {

    private Logger logger = LoggerFactory.getLogger(ExceptionTestApiTest.class);

    @Autowired
    private MockMvc mockMvc;
    private static final String API = "/exceptionTestApi";


    @Test
    void missingServletRequestParameterException() throws Exception {
        //没有参数的校验
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(API + "/missingServletRequestParameterException");//.param("test", "a54");
        String str = mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        ResponseResult responseResult = JsonUtils.parseObject(str, ResponseResult.class);
       // Assertions.assertEquals(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(),
       //         (int) responseResult.getCode());
       // Assertions.assertEquals(MessageFormat.format(GlobalExceptionHandler
        // .MISSING_PARAMETER_EXCEPTION_TIP, "test"), responseResult.getMessage());
        //有参数
        builder = MockMvcRequestBuilders.get(API + "/missingServletRequestParameterException").param("test", "123");
        str = mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        responseResult = JsonUtils.parseObject(str, ResponseResult.class);
        Assertions.assertEquals(ResponseStatusEnum.SUCCESS.getCode(), (int) responseResult.getCode());
    }

    @Test
    void constraintViolationException() throws Exception {
        //长度<10
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(API + "/constraintViolationException")
                .param("test", "a54")
                .param("test2", "2");
        String str = mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        ResponseResult responseResult = JsonUtils.parseObject(str, ResponseResult.class);
        logger.info(JsonUtils.toJsonString(responseResult));
       // Assertions.assertEquals(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(),
       //         (int) responseResult.getCode());
        Assertions.assertEquals("最小长度为10", responseResult.getMessage());
        //yes
        builder = MockMvcRequestBuilders.get(API + "/constraintViolationException")
                .param("test", "1".repeat(11))
                .param("test2", "1".repeat(20));
        str = mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        responseResult = JsonUtils.parseObject(str, ResponseResult.class);
        logger.info(JsonUtils.toJsonString(responseResult));
        Assertions.assertEquals(ResponseStatusEnum.SUCCESS.getCode(), (int) responseResult.getCode());
    }

    @Test
    void methodArgumentNotValidException() throws Exception {
        User user = new User();
        user.setUsername("1");
        user.setPassword("2");
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(API + "/methodArgumentNotValidException")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJsonString(user));
        String str = mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        ResponseResult responseResult = JsonUtils.parseObject(str, ResponseResult.class);
        logger.info(JsonUtils.toJsonString(responseResult));
        //Assertions.assertEquals(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(),
        //        (int) responseResult.getCode());
        Assertions.assertEquals("password在5~10位", responseResult.getMessage());
    }

    @Test
    void bindException() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(API + "/bindException")
                .param("username", "12")
                .param("password", "23");
        String str = mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        ResponseResult responseResult = JsonUtils.parseObject(str, ResponseResult.class);
        logger.info(JsonUtils.toJsonString(responseResult));
       // Assertions.assertEquals(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(),
      //          (int) responseResult.getCode());
        Assertions.assertEquals("password在5~10位", responseResult.getMessage());
    }
}