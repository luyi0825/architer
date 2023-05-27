package io.github.arthiters.test.file.domain.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class FileUser {

    @ExcelProperty("用户名")
    private String userName;

    /**
     * 密码
     */
    @ExcelProperty("密码")
    private String password;

    @ExcelProperty("地址")
    private String address;
}
