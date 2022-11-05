package io.github.architers.common.module.tenant.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author luyi
 */
@Data
public abstract class BaseEntity implements Serializable {
    /**
     * 是否删除:0否1是
     */
    @TableLogic
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


    /**
     * 得到主键ID
     *
     * @return 主键ID
     */
    public abstract Number getId();

    /**
     * 填充更新创建字段信息
     */
    public BaseEntity fillCreateAndUpdateField(Date date) {
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
