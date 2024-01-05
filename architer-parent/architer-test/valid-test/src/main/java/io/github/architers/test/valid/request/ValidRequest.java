package io.github.architers.test.valid.request;


import io.github.architers.context.valid.annotion.EnumValue;
import io.github.architers.test.valid.eums.YesOrNo;
import lombok.Data;

@Data
public class ValidRequest {

    /**
     * 是否删除
     */
    @EnumValue(value = YesOrNo.class, checkField = "code")
    private Byte isDeleted;

    /**
     * 是否结束
     */
    @EnumValue(value = YesOrNo.class, checkField = "code", force = true)
    private Integer isEnd;

    @EnumValue(value = YesOrNo.class)
    private String booleanCaption;
}
