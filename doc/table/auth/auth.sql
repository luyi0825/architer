CREATE TABLE `t_auth_user_1`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`      varchar(100) NOT NULL COMMENT '用户名',
    `password`      varchar(50)  NOT NULL COMMENT '密码',
    `mail`          varchar(50)  NOT NULL COMMENT '邮件',
    `lastLoginIp`   varchar(100) DEFAULT NULL COMMENT '最后登录IP',
    `errorCount`    smallint(6) NOT NULL DEFAULT 0 COMMENT '错误次数',
    `lastLoginTime` date         DEFAULT NULL COMMENT '最后登录时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3