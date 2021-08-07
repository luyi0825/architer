#----------------------------如果不存在就创建数据库-
Create
    database If Not Exists t_base Character Set utf8mb3;
use t_base;
#----------------------------t_base_code_map#代码集

drop table if exists t_base_code_map;
CREATE TABLE `t_base_code_map`
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
  DEFAULT CHARSET = utf8mb3;

#-----------------------------t_base_code_map_item#代码集项

drop table if exists t_base_code_map_item;
CREATE TABLE `t_base_code_map_item`
(
    `id`           bigint unsigned primary key AUTO_INCREMENT COMMENT '主键ID',
    `item_code`    varchar(30) NOT NULL COMMENT '编码',
    `code`         varchar(30) NOT NULL COMMENT 't_base_code_map编码',
    `item_caption` varchar(50) NOT NULL COMMENT '对应中文',
    `remark`       varchar(100) DEFAULT NULL COMMENT '备注',
    `update_time`  date         DEFAULT NULL COMMENT '更新时间',
    `create_time`  date         DEFAULT NULL COMMENT '创建时间',
    `update_user`  varchar(100) DEFAULT NULL COMMENT '更新人',
    `create_user`  varchar(100) DEFAULT NULL COMMENT '创建人',
    #增加外键
    FOREIGN KEY (code) REFERENCES t_base_code_map (code),
    ## 唯一索引
    unique index t_base_code_map_code_item_code (code, item_code)
) ENGINE = InnoDB COMMENT ='代码集项'
  DEFAULT CHARSET = utf8mb3;
#-----------------------------t_base_standard_area#标准区划

CREATE TABLE `t_base_standard_area`
(
    `id`           smallint(6) primary key COMMENT '主键ID',
    `area_id`      char(12) unique NOT NULL COMMENT '区划ID',
    `parent_id`    smallint(6)     NOT NULL COMMENT '父级ID',
    `caption`      varchar(20)     NOT NULL COMMENT '区划名称',
    `full_caption` varchar(50)     NOT NULL COMMENT '区划全称'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3 COMMENT ='标准区划'