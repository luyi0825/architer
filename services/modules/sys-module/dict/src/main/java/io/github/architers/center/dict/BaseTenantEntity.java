package io.github.architers.center.dict;

import lombok.Data;

import java.util.Date;

/**
 * @author luyi
 */
@Data
public abstract class BaseTenantEntity {
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
    protected Long createBy;

    /**
     * 创建时间
     */
    protected Date updateTime;

    /**
     * 创建人
     */
    protected Long updateBy;

    /**
     * 租户ID
     */
    protected Integer tenantId;

    /**
     * 得到主键ID
     *
     * @return 主键ID
     */
    public abstract Number getId();

    /**
     * 填充更新创建字段信息
     */
    public BaseTenantEntity fillCreateAndUpdateField(Date date) {
        if (date == null) {
            date = new Date();
        }
        if (this.getId() == null) {
            this.createBy = 0L;//@TODO
            this.createTime = date;
            this.updateBy = 0L;//@TODO
            this.updateTime = date;
        } else {
            this.updateBy = 0L;//@TODO
            this.updateTime = date;
        }
        return this;
    }


}
