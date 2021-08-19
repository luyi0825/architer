package com.business.base.codemap.api.codemapitem;

import cn.hutool.core.io.FileUtil;
import com.architecture.context.common.ResponseStatusEnum;
import com.architecture.context.common.response.ResponseResult;
import com.architecture.utils.JsonUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.base.codemap.api.CodeMapItemApiTest;
import com.business.base.codemap.constants.CodeMapItemValidConstant;
import com.business.base.codemap.entity.CodeMapItem;
import com.business.base.codemap.service.CodeMapItemService;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class CodeMapItemUpdateTest {
    @Autowired
    private MockMvc mvc;

    @Value("classpath:CodeMapItem_test2.json")
    private Resource resource;

    private String codeMapItemStr;

    @Autowired
    private CodeMapItemService codeMapItemService;

    @PostConstruct
    public void init() throws IOException {
        codeMapItemStr = FileUtil.readUtf8String(resource.getFile());
    }

    public void startTest() throws Exception {
        testId();
        testCode();
        testItemCode();
        testItemCaption();
        testRemark();
        update();
    }

    /**
     * 测试ID
     */
    private void testId() throws Exception {
        List<CodeMapItem> codeMapItems = JsonUtils.readListValue(codeMapItemStr, CodeMapItem.class);
        CodeMapItem codeMapItem = codeMapItems.get(0);
        codeMapItem.setId(null);
        ResponseResult responseResult = this.doUpdate(codeMapItem);
        Assertions.assertEquals(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(), responseResult.getCode());
        Assertions.assertEquals(CodeMapItemValidConstant.ID_NOT_NULL, responseResult.getMessage());
    }


    private void update() throws Exception {
        List<CodeMapItem> codeMapItems = JsonUtils.readListValue(codeMapItemStr, CodeMapItem.class);
        CodeMapItem codeMapItem = codeMapItems.get(0);
        QueryWrapper<CodeMapItem> codeMapItemQueryWrapper = new QueryWrapper<>();
        codeMapItemQueryWrapper.eq("code", codeMapItem.getCode());
        codeMapItemQueryWrapper.eq("item_code", codeMapItem.getItemCode());
        codeMapItem = codeMapItemService.selectOne(codeMapItemQueryWrapper);
        codeMapItem.setUpdateUser("test");
        this.doUpdate(codeMapItem);
        ResponseResult responseResult = this.doUpdate(codeMapItem);
        Assertions.assertEquals(ResponseStatusEnum.SUCCESS.getCode(), responseResult.getCode());
    }

    /**
     * 测试remark
     */
    private void testRemark() throws Exception {
        List<CodeMapItem> codeMapItems = JsonUtils.readListValue(codeMapItemStr, CodeMapItem.class);
        CodeMapItem codeMapItem = codeMapItems.get(0);
        codeMapItem.setRemark("0".repeat(101));
        codeMapItem.setId(1L);
        ResponseResult responseResult = this.doUpdate(codeMapItem);
        Assertions.assertEquals(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(), responseResult.getCode());
        Assertions.assertEquals(CodeMapItemValidConstant.REMARK_LENGTH_LIMIT, responseResult.getMessage());
    }


    /**
     * 测试itemCaption
     */
    private void testItemCaption() throws Exception {
        List<CodeMapItem> codeMapItems = JsonUtils.readListValue(codeMapItemStr, CodeMapItem.class);
        CodeMapItem codeMapItem = codeMapItems.get(0);
        codeMapItem.setId(1L);
        //空
        codeMapItem.setItemCaption("");
        ResponseResult responseResult = this.doUpdate(codeMapItem);
        Assertions.assertEquals(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(), responseResult.getCode());
        Assertions.assertEquals(CodeMapItemValidConstant.ITEM_CAPTION_NOT_BLANK, responseResult.getMessage());

        //长度
        codeMapItem.setItemCaption("0".repeat(51));
        responseResult = this.doUpdate(codeMapItem);
        Assertions.assertEquals(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(), responseResult.getCode());
        Assertions.assertEquals(CodeMapItemValidConstant.ITEM_CAPTION_LENGTH_LIMIT, responseResult.getMessage());
    }

    /**
     * 测试itemCode
     */
    private void testItemCode() throws Exception {
        List<CodeMapItem> codeMapItems = JsonUtils.readListValue(codeMapItemStr, CodeMapItem.class);
        CodeMapItem codeMapItem = codeMapItems.get(0);
        codeMapItem.setId(1L);
        //空
        codeMapItem.setItemCode("");
        ResponseResult responseResult = this.doUpdate(codeMapItem);
        Assertions.assertEquals(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(), responseResult.getCode());
        Assertions.assertEquals(CodeMapItemValidConstant.ITEM_CODE_NOT_BLANK, responseResult.getMessage());

        //长度
        codeMapItem.setItemCode("0".repeat(31));
        responseResult = this.doUpdate(codeMapItem);
        Assertions.assertEquals(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(), responseResult.getCode());
        Assertions.assertEquals(CodeMapItemValidConstant.ITEM_CODE_LENGTH_LIMIT, responseResult.getMessage());
    }

    /**
     * 测试code
     */
    private void testCode() throws Exception {
        List<CodeMapItem> codeMapItems = JsonUtils.readListValue(codeMapItemStr, CodeMapItem.class);
        CodeMapItem codeMapItem = codeMapItems.get(0);
        codeMapItem.setCode(null);
        codeMapItem.setId(1L);
        ResponseResult responseResult = doUpdate(codeMapItem);
        Assertions.assertEquals(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(), (int) responseResult.getCode());
        Assertions.assertEquals(CodeMapItemValidConstant.CODE_NOT_BLANK, responseResult.getMessage());
    }

    /**
     * 执行添加
     */
    private ResponseResult doUpdate(CodeMapItem codeMapItem) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post(CodeMapItemApiTest.apiRequestMapping + "/update").contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJsonString(codeMapItem));
        String returnStr = mvc.perform(mockHttpServletRequestBuilder).andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        return JsonUtils.readValue(returnStr, ResponseResult.class);
    }
}
