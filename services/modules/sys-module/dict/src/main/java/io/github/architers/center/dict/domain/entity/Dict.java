package io.github.architers.center.dict.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.architers.center.dict.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author luyi
 */
@TableName("sys_dict")
@Data
public class Dict extends BaseEntity implements Serializable {

    /**
     * 数据字典主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户ID
     */
    private Integer tenantId;

    /**
     * 字典编码
     */
    private String dictCode;
    /**
     * 字典名称
     */
    private String dictCaption;
    /**
     * 备注
     */
    private String remark;

}
