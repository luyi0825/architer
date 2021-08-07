package com.business.base.codemap.api.codemapitem;

import cn.hutool.core.io.FileUtil;
import com.architecture.ultimate.module.common.ResponseStatusEnum;
import com.architecture.ultimate.module.common.exception.ParamsValidException;
import com.architecture.ultimate.module.common.response.ResponseResult;
import com.architecture.ultimate.utils.JsonUtils;
import com.business.base.codemap.api.CodeMapItemApiTest;
import com.business.base.codemap.constants.CodeMapItemValidConstant;
import com.business.base.codemap.entity.CodeMapItem;
import com.business.base.codemap.service.CodeMapItemService;
import com.business.base.codemap.service.CodeMapService;
import com.business.base.codemap.service.CodeMapServiceTest;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class CodeMapItemAddTest {
    @Autowired
    private MockMvc mvc;

    @Value("classpath:CodeMapItem_test2.json")
    private Resource resource;

    private String codeMapItemStr;

    @Autowired
    private CodeMapServiceTest codeMapServiceTest;

    @PostConstruct
    public void init() throws IOException {
        codeMapItemStr = FileUtil.readUtf8String(resource.getFile());
    }

    public void startTest() throws Exception {
        testCode();
        testItemCode();
        testItemCaption();
        testRemark();
        add();
    }


    private void add() throws Exception {
        List<CodeMapItem> codeMapItems = JsonUtils.readListValue(codeMapItemStr, CodeMapItem.class);
        CodeMapItem codeMapItem = codeMapItems.get(0);
        this.doAdd(codeMapItem);
        ResponseResult responseResult = this.doAdd(codeMapItem);
        Assertions.assertEquals(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(), responseResult.getCode());
        Assertions.assertEquals(MessageFormat.format(CodeMapItemValidConstant.ITEM_CODE_EXIST, codeMapItem.getItemCode()), responseResult.getMessage());

    }

    /**
     * 测试remark
     */
    private void testRemark() throws Exception {
        List<CodeMapItem> codeMapItems = JsonUtils.readListValue(codeMapItemStr, CodeMapItem.class);
        CodeMapItem codeMapItem = codeMapItems.get(0);
        codeMapItem.setRemark("0".repeat(101));
        ResponseResult responseResult = this.doAdd(codeMapItem);
        Assertions.assertEquals(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(), responseResult.getCode());
        Assertions.assertEquals(CodeMapItemValidConstant.REMARK_LENGTH_LIMIT, responseResult.getMessage());
    }


    /**
     * 测试itemCaption
     */
    private void testItemCaption() throws Exception {
        List<CodeMapItem> codeMapItems = JsonUtils.readListValue(codeMapItemStr, CodeMapItem.class);
        CodeMapItem codeMapItem = codeMapItems.get(0);
        //空
        codeMapItem.setItemCaption("");
        ResponseResult responseResult = this.doAdd(codeMapItem);
        Assertions.assertEquals(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(), responseResult.getCode());
        Assertions.assertEquals(CodeMapItemValidConstant.ITEM_CAPTION_NOT_BLANK, responseResult.getMessage());

        //长度
        codeMapItem.setItemCaption("0".repeat(51));
        responseResult = this.doAdd(codeMapItem);
        Assertions.assertEquals(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(), responseResult.getCode());
        Assertions.assertEquals(CodeMapItemValidConstant.ITEM_CAPTION_LENGTH_LIMIT, responseResult.getMessage());
    }

    /**
     * 测试itemCode
     */
    private void testItemCode() throws Exception {
        List<CodeMapItem> codeMapItems = JsonUtils.readListValue(codeMapItemStr, CodeMapItem.class);
        CodeMapItem codeMapItem = codeMapItems.get(0);
        //空
        codeMapItem.setItemCode("");
        ResponseResult responseResult = this.doAdd(codeMapItem);
        Assertions.assertEquals(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(), responseResult.getCode());
        Assertions.assertEquals(CodeMapItemValidConstant.ITEM_CODE_NOT_BLANK, responseResult.getMessage());

        //长度
        codeMapItem.setItemCode("0".repeat(31));
        responseResult = this.doAdd(codeMapItem);
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
        ResponseResult responseResult = doAdd(codeMapItem);
        Assertions.assertEquals(ResponseStatusEnum.PARAMS_VALID_EXCEPTION.getCode(), (int) responseResult.getCode());
        Assertions.assertEquals(CodeMapItemValidConstant.CODE_NOT_BLANK, responseResult.getMessage());
    }

    /**
     * 执行添加
     */
    public ResponseResult doAdd(CodeMapItem codeMapItem) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post(CodeMapItemApiTest.apiRequestMapping + "/add").contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJsonString(codeMapItem));
        String returnStr = mvc.perform(mockHttpServletRequestBuilder).andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        return JsonUtils.readValue(returnStr, ResponseResult.class);
    }
}
