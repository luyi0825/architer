drop table if exists t_base_code_convert;
CREATE TABLE `t_base_code_convert`
(
    `id`          bigint unsigned primary key AUTO_INCREMENT COMMENT '主键ID',
    `code`        varchar(30) unique NOT NULL COMMENT '编码',
    `caption`     varchar(50)        NOT NULL,
    `remark`      varchar(100) DEFAULT NULL COMMENT '备注',
    `update_time` date         DEFAULT NULL COMMENT '更新时间',
    `create_time` date         DEFAULT NULL COMMENT '创建时间',
    `update_user` varchar(100) DEFAULT NULL COMMENT '更新人',
    `create_user` varchar(100) DEFAULT NULL COMMENT '创建人'
) ENGINE = InnoDB COMMENT ='代码集'
  DEFAULT CHARSET = utf8mb3