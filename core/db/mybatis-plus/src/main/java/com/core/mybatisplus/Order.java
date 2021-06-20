package com.core.mybatisplus;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luyi
 * @date 2020/12/27
 */
@Data
public class Order implements Serializable {

    private String field;
    private OrderEnum order;

}
