package com.core.test.valid.vo;


import com.core.service.valid.ListValue;
import com.core.service.valid.group.AddGroup;
import lombok.Data;

/**
 * @author luyi
 * 用户VO
 */
@Data
public class User {
//    @Null(message = "用户ID必须为空", groups = AddGroup.class)
//    @NotNull(message = "用户ID必须为空", groups = UpdateGroup.class)
//    private String userId;
//
//    @Size(min = 5, message = "用户名的长度不能<5", groups = AddGroup.class)
//    private String username;
//    @Min(message = "年龄不能小于0", value = 1, groups = AddGroup.class)
//    @Max(message = "年龄不能大于150", value = 150, groups = AddGroup.class)
//    private Integer age;
//
//    @Email(message = "邮箱格式不正确", groups = AddGroup.class)
//    private String email;

    @ListValue(message = "状态不合法", groups = AddGroup.class, value = {"0", "1"})
    private int status;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
