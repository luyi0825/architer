#创建数据库
create database `t_auth`;

#创建权限表
CREATE TABLE `t_auth_user`
(
    `id`              bigint(20) unsigned COMMENT '主键',
    `username`        varchar(100) NOT NULL COMMENT '用户名',
    `password`        varchar(50)  NOT NULL COMMENT '密码',
    `mail`            varchar(50)  NOT NULL COMMENT '邮件',
    `last_login_ip`   varchar(100)          DEFAULT NULL COMMENT '最后登录IP',
    `error_count`     smallint(6)  NOT NULL DEFAULT 0 COMMENT '错误次数',
    `last_login_time` date                  DEFAULT NULL COMMENT '最后登录时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3