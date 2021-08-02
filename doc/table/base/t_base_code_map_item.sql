drop table if exists t_base_code_map_item;
CREATE TABLE `t_base_code_map_item`
(
    `id`           bigint unsigned primary key AUTO_INCREMENT COMMENT '主键ID',
    `item_code`    varchar(30) NOT NULL COMMENT '编码',
    `convert_code` varchar(30) NOT NULL COMMENT 't_base_code_convert编码',
    `caption`      varchar(50) NOT NULL COMMENT '对应中文',
    `remark`       varchar(100) DEFAULT NULL COMMENT '备注',
    `update_time`  date         DEFAULT NULL COMMENT '更新时间',
    `create_time`  date         DEFAULT NULL COMMENT '创建时间',
    `update_user`  varchar(100) DEFAULT NULL COMMENT '更新人',
    `create_user`  varchar(100) DEFAULT NULL COMMENT '创建人',
    #增加外键
    FOREIGN KEY (convert_code) REFERENCES t_base_code_convert (code),
    ##唯一键
    unique key (convert_code, item_code)
) ENGINE = InnoDB COMMENT ='代码集项'
  DEFAULT CHARSET = utf8mb3