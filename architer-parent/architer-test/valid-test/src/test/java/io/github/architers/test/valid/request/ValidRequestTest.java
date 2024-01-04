package io.github.architers.test.valid.request;

import io.github.architers.context.valid.BeanValidatorUtils;
import io.github.architers.test.valid.eums.YesOrNo;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidRequestTest {

    @Test
    public void testEnumValue() {
        ValidRequest validRequest = new ValidRequest();
        validRequest.setIsDeleted(1);
        BeanValidatorUtils.validateEntity(validRequest);
    }
}