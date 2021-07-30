package com.business.base.area.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author luyi
 * 标准区划实体
 */
@Data
@TableName("t_standard_area")
public class StandardArea implements Serializable {
    /**
     * 区划ID
     */
    private String areaId;
    /**
     * 父级ID
     */
    private String parentId;
    /**
     * 区划名称
     */
    private String caption;
    /**
     * 区划全称
     */
    private String fullCaption;
}
