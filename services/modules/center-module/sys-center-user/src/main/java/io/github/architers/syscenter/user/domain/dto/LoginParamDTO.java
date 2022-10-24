package io.github.architers.syscenter.user.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luyi
 */
@Data
public class LoginParamDTO implements Serializable {
    private String userName;
    private String password;
}
