package io.github.architers.context.autocode;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author luyi
 *
 */
@Data
public class BaseEntity {

    /**
     * 是否删除:0否1是
     */
    protected boolean deleted;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date createTime;

    /**
     * 创建人
     */
    protected Long createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date updateTime;

    /**
     * 创建人
     */
    protected Long updateBy;



}
