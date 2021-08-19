package com.business.base.codemap.api.codemap;

import cn.hutool.core.io.FileUtil;
import com.architecture.context.common.response.ResponseResult;
import com.architecture.utils.JsonUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.base.codemap.api.CodeMapApiTest;
import com.business.base.codemap.constants.CodeMapValidConstant;
import com.business.base.codemap.entity.CodeMap;
import com.business.base.codemap.service.CodeMapService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * CodeMap更新测试
 */
@Component
public class CodeMapApiUpdate {

    private final MockMvc mvc;
    private final CodeMapService codeMapService;
    @Value("classpath:CodeMap_test1.json")
    private Resource resource;

    private String codeMapStr;

    public CodeMapApiUpdate(MockMvc mvc, CodeMapService codeMapService) {
        this.mvc = mvc;
        this.codeMapService = codeMapService;
    }

    @PostConstruct
    public void init() throws IOException {
        this.codeMapStr = FileUtil.readUtf8String(resource.getFile());
    }


    public void startTest() throws Exception {
        testId();
        testCode();
        testCaption();
        testRemark();
        //更新
        startUpdate();

    }

    private void startUpdate() throws Exception {
        CodeMap codeMap = getDbCodeMap();
        codeMap.setUpdateUser("test");
        ResponseResult responseResult = this.getUpdateResponseResult(codeMap);
        assertEquals(200, responseResult.getCode());
    }

    /**
     * 测试主键
     */
    private void testId() throws Exception {
        CodeMap codeMap = getDbCodeMap();
        codeMap.setId(null);
        ResponseResult responseResult = getUpdateResponseResult(codeMap);
        assertEquals(100, (int) responseResult.getCode());
        assertEquals(CodeMapValidConstant.ID_NOT_NULL, responseResult.getMessage());
    }

    /**
     * 测试备注
     */
    private void testRemark() throws Exception {
        CodeMap codeMap = getDbCodeMap();
        codeMap.setRemark("0".repeat(101));
        ResponseResult responseResult = getUpdateResponseResult(codeMap);
        assertEquals(100, (int) responseResult.getCode());
        assertEquals(CodeMapValidConstant.REMARK_LENGTH_LIMIT, responseResult.getMessage());
    }

    /**
     * 测试中文名称
     */
    private void testCaption() throws Exception {
        CodeMap codeMap = getDbCodeMap();
        codeMap.setCaption(null);
        ResponseResult responseResult = getUpdateResponseResult(codeMap);
        assertEquals(100, (int) responseResult.getCode());
        assertEquals(CodeMapValidConstant.CAPTION_NOT_BLANK, responseResult.getMessage());
        //caption的长度太大
        codeMap.setCaption("0".repeat(51));
        responseResult = getUpdateResponseResult(codeMap);
        assertEquals(100, (int) responseResult.getCode());
        assertEquals(CodeMapValidConstant.CAPTION_LENGTH_LIMIT, responseResult.getMessage());
    }


    /**
     * 测试code
     */
    private void testCode() throws Exception {
        CodeMap codeMap = getDbCodeMap();
        //code为空
        codeMap.setCode(null);
        ResponseResult responseResult = getUpdateResponseResult(codeMap);
        assertEquals(100, (int) responseResult.getCode());
        assertEquals(CodeMapValidConstant.CODE_NOT_BLANK, responseResult.getMessage());
        //code的长度太小
        codeMap.setCode("1");
        responseResult = getUpdateResponseResult(codeMap);
        assertEquals(100, (int) responseResult.getCode());
        assertEquals(CodeMapValidConstant.CODE_LENGTH_LIMIT, responseResult.getMessage());
        //code的长度太大
        codeMap.setCode("0".repeat(31));
        responseResult = getUpdateResponseResult(codeMap);
        assertEquals(100, (int) responseResult.getCode());
        assertEquals(CodeMapValidConstant.CODE_LENGTH_LIMIT, responseResult.getMessage());
    }

    /**
     * 得到添加的响应结果
     */
    private ResponseResult getUpdateResponseResult(CodeMap codeMap) throws Exception {
        String updateUrl = CodeMapApiTest.requestMapping + (codeMap.getId() == null ? "/update/1" : "/update/" + codeMap.getId());
        String returnStr = mvc.perform(post(updateUrl).contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJsonString(codeMap)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return JsonUtils.readValue(returnStr, ResponseResult.class);
    }


    private CodeMap getDbCodeMap() {
        CodeMap codeMap = JsonUtils.readValue(codeMapStr, CodeMap.class);
        QueryWrapper<CodeMap> codeMapQueryWrapper = new QueryWrapper<>();
        codeMapQueryWrapper.eq("code", codeMap.getCode());
        return codeMapService.selectOne(codeMapQueryWrapper);
    }

}
