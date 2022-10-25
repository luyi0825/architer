package io.github.architers.syscenter.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysMenuListColumnConfig {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 展示的名称
     */
    private String showName;
    /**
     * 自定义宽
     */
    private String width;

    /**
     * 顺序
     */
    private int order;
}
