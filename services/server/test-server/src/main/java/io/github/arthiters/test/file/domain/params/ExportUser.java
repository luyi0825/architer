package io.github.arthiters.test.file.domain.params;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExportUser implements Serializable {

    /**
     * 用户名
     */
     private String userName;


    public ExportUser() {

    }
}
