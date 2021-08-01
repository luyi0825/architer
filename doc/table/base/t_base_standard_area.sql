CREATE TABLE `t_base_standard_area`
(
    `id`           smallint(6) primary key COMMENT '主键ID',
    `area_id`      char(12) unique NOT NULL COMMENT '区划ID',
    `parent_id`    smallint(6) NOT NULL COMMENT '父级ID',
    `caption`      varchar(20)     NOT NULL COMMENT '区划名称',
    `full_caption` varchar(50)     NOT NULL COMMENT '区划全称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='标准区划'