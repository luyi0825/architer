package io.github.architers.center.dict.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author luyi
 */
@Data
public abstract class BaseEntity {
    /**
     * 是否删除:0否1是
     */
    protected boolean deleted;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 创建人
     */
    protected String createBy;

    /**
     * 创建时间
     */
    protected Date updateTime;

    /**
     * 创建人
     */
    protected String updateBy;


    /**
     * 得到主键ID
     *
     * @return 主键ID
     */
    public abstract Number getId();

    /**
     * 填充更新创建字段信息
     */
    public BaseEntity fillCreateOrUpdateField(Date date) {
        if (date == null) {
            date = new Date();
        }
        if (this.getId() == null) {
            this.createBy = "";//@TODO
            this.createTime = date;
        } else {
            this.updateBy = "";//@TODO
            this.updateTime = date;
        }
        return this;
    }


}
