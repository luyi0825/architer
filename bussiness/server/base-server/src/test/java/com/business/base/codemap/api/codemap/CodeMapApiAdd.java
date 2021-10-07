package com.business.base.codemap.api.codemap;

import cn.hutool.core.io.FileUtil;
import com.architecture.context.common.response.ResponseResult;
import com.architecture.utils.JsonUtils;
import com.business.base.codemap.api.CodeMapApiTest;
import com.business.base.codemap.constants.CodeMapValidConstant;
import com.business.base.codemap.entity.CodeMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * CodeMap添加测试
 */
@Component
public class CodeMapApiAdd {

    @Value("classpath:CodeMap_test1.json")
    private Resource resource;

    private final MockMvc mvc;
    private String codeMapStr;

    public CodeMapApiAdd(MockMvc mvc) {
        this.mvc = mvc;
    }

    @PostConstruct
    public void init() throws IOException {
        this.codeMapStr = FileUtil.readUtf8String(resource.getFile());
    }

    public void startTest() throws Exception {
        testRepeatAdd(codeMapStr);
        testCode(codeMapStr);
        testCaption(codeMapStr);
        testRemark(codeMapStr);
    }

    /**
     * 测试备注
     */
    private void testRemark(String codeMapStr) throws Exception {
        CodeMap codeMap = JsonUtils.readValue(codeMapStr, CodeMap.class);
        codeMap.setRemark("0".repeat(101));
        ResponseResult responseResult = getAddResponseResult(JsonUtils.toJsonString(codeMap));
        assertEquals(100, (int) responseResult.getCode());
        assertEquals(CodeMapValidConstant.REMARK_LENGTH_LIMIT, responseResult.getMessage());
    }

    /**
     * 测试中文名称
     */
    private void testCaption(String codeMapStr) throws Exception {
        CodeMap codeMap = JsonUtils.readValue(codeMapStr, CodeMap.class);
        codeMap.setCaption(null);
        ResponseResult responseResult = getAddResponseResult(JsonUtils.toJsonString(codeMap));
        assertEquals(100, (int) responseResult.getCode());
        assertEquals(CodeMapValidConstant.CAPTION_NOT_BLANK, responseResult.getMessage());
        //caption的长度太大
        codeMap.setCaption("0".repeat(51));
        responseResult = getAddResponseResult(JsonUtils.toJsonString(codeMap));
        assertEquals(100, (int) responseResult.getCode());
        assertEquals(CodeMapValidConstant.CAPTION_LENGTH_LIMIT, responseResult.getMessage());
    }

    /**
     * 测试重复添加: 连续添加两次,code重复验证
     */
    private void testRepeatAdd(String addStr) throws Exception {
        getAddResponseResult(addStr);
        ResponseResult responseResult = getAddResponseResult(addStr);
        assertEquals(100, (int) responseResult.getCode());
        assertTrue(responseResult.getMessage().contains("已经存在"));
    }

    /**
     * 测试code
     */
    private void testCode(String codeMapStr) throws Exception {
        CodeMap codeMap = JsonUtils.readValue(codeMapStr, CodeMap.class);
        //code为空
        codeMap.setCode(null);
        ResponseResult responseResult = getAddResponseResult(JsonUtils.toJsonString(codeMap));
        assertEquals(100, (int) responseResult.getCode());
        assertEquals(CodeMapValidConstant.CODE_NOT_BLANK, responseResult.getMessage());
        //code的长度太小
        codeMap.setCode("1");
        responseResult = getAddResponseResult(JsonUtils.toJsonString(codeMap));
        assertEquals(100, (int) responseResult.getCode());
        assertEquals(CodeMapValidConstant.CODE_LENGTH_LIMIT, responseResult.getMessage());
        //code的长度太大
        codeMap.setCode("0".repeat(31));
        responseResult = getAddResponseResult(JsonUtils.toJsonString(codeMap));
        assertEquals(100, (int) responseResult.getCode());
        assertEquals(CodeMapValidConstant.CODE_LENGTH_LIMIT, responseResult.getMessage());
    }

    /**
     * 得到添加的响应结果
     */
    private ResponseResult getAddResponseResult(String addStr) throws Exception {
        String returnStr = mvc.perform(post(CodeMapApiTest.requestMapping + "/add").contentType(MediaType.APPLICATION_JSON).content(addStr))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return JsonUtils.readValue(returnStr, ResponseResult.class);
    }

}
