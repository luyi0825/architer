package io.github.architers.test.valid.request;


import io.github.architers.context.valid.EnumValue;
import io.github.architers.test.valid.eums.YesOrNo;
import lombok.Data;
import lombok.Getter;

@Data
public class ValidRequest {

    /**
     * 是否删除
     */
    @EnumValue(value = YesOrNo.class, field = "code")
    private Integer isDeleted;
}
