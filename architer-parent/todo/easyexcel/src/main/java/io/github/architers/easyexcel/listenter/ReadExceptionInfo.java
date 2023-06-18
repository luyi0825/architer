package io.github.architers.easyexcel.listenter;

import lombok.Data;

import java.io.Serializable;

/**
 * 读取异常的数据
 *
 * @author luyi
 */
@Data
public class ReadExceptionInfo implements Serializable {

    /**
     * 表头
     */
    private String head;

    /**
     * 对应的行
     */
    private int row;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 原始的异常信息
     */
    private Exception originalException;

    @Override
    public String toString() {
        return "ReadExceptionInfo{" +
                "head='" + head + '\'' +
                ", row=" + row +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
