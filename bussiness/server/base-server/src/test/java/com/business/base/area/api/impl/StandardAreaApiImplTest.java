package com.business.base.area.api.impl;


import com.architecture.ultimate.module.common.response.ResponseResult;
import com.business.base.area.entity.StandardArea;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * 标准区划测试类
 *
 * @author luyi
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StandardAreaApiImplTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mvc;
    private final int id = 1;

    @Test
    void findByParentId() throws Exception {
        String returnStr = mvc.perform(get("/standardAreaApi/findByParentId/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ResponseResult baseResponse = objectMapper.readValue(returnStr, ResponseResult.class);
        assertThat(baseResponse.getCode()).isEqualTo(200);
       // assertThat(baseResponse.getData()).isNotNull();
    }


    @Test
    void findById() throws Exception {
        findById(id);
    }

    private StandardArea findById(int tempId) throws Exception {
        //先查询出原来的数据
        String returnStr = mvc.perform(get("/standardAreaApi/findById/" + tempId))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ResponseResult responseResult = objectMapper.readValue(returnStr, ResponseResult.class);
        assertThat(responseResult.getCode()).isEqualTo(200);
        assertThat(responseResult.getData()).isNotNull();
        return objectMapper.readValue(objectMapper.writeValueAsString(responseResult.getData()), StandardArea.class);
    }

    @Test
    void update() throws Exception {
        StandardArea standardArea = findById(id);
        //更新
        standardArea.setFullCaption(standardArea.getFullCaption() + "_" + standardArea.getFullCaption());
        mvc.perform(post("/standardAreaApi/update/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(standardArea))).andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        mvc.perform(post("/standardAreaApi/delete/" + id)).andExpect(status().isOk());
    }
}