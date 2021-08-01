package com.business.base.code.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_base_code_convert")
public class CodeConvert {
    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 编码
     */
    private String code;
    /**
     * 中文描述
     */
    private String caption;
    /**
     * 备注
     */
    private String remark;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 添加人
     */
    private String createUser;
    /**
     * 添加时间
     */
    private Date createTime;
}
