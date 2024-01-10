package io.github.architers.test.valid.request;

import io.github.architers.common.json.JsonUtils;
import io.github.architers.context.valid.BeanValidatorUtils;
import org.junit.Test;

public class ValidRequestTest {

    /**
     * 强制校验
     */
    @Test
    public void testForce() {
        ValidRequest validRequest = new ValidRequest();
        validRequest.setIsEnd(1);
        System.out.println(BeanValidatorUtils.validate(validRequest).get("isEnd"));
    }

    @Test
    public void testEnumValue_YES() {
        ValidRequest validRequest = new ValidRequest();
        validRequest.setIsDeleted((byte) 1);
        System.out.println(JsonUtils.toJsonString(BeanValidatorUtils.validate(validRequest)));
    }

    /**
     * 校验接口指定参数
     */
    @Test
    public void testGetValidField(){
        ValidRequest validRequest = new ValidRequest();
        validRequest.setIsDeleted((byte) 1);
        validRequest.setIsEnd(1);
        validRequest.setBooleanCaption("1");
        System.out.println(JsonUtils.toJsonString(BeanValidatorUtils.validate(validRequest)));

        validRequest.setBooleanCaption("是");
        System.out.println(JsonUtils.toJsonString(BeanValidatorUtils.validate(validRequest)));
    }

}