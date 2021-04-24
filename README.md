# 核心包

一些都是为了简化,通用.

## authority

权限模块

## boot

启动相关

### module-builder

模块构建，扫表子模块用

## cache

根据注解，实现系统缓存

- 支持自定义锁的类型（默认无锁，根据锁解决缓存击穿）
- 支持自定义缓存的随机时间（解决缓存雪崩）
- 也解决了缓存穿透的问题
- 自定义缓存key, 支持SpEl表达式的配合(前缀+后缀)
- 支持配置Proxy或者aspectj
- 支持配置异步缓存处理

目前待完成：

- 如果解决多个缓存的注解的配置
- 异步缓存的问题
- 自定义缓存值（目前值是返回值）

### cache-common

缓存公共包 主要写各种缓存公共代码

### local-cache

本地缓存，主要的功能如下：

- 单体机缓存的解决方案，可以设置缓存的过期时间
- 可以设置缓存的淘汰策略（模仿redis--还在实现中）
- 支持持久化（实现中。。。）

### redis-cache

redis 缓存

## excel

excel操作的模块。技术选型用的easyexcel(poi容易引发oom)

### html-xls

将html转换为xls：

- 支持复杂的合并
- 支持自定义的html样式解析（还没有完善）

## lock

锁

### base-lock

锁的接口层

### lock-distributed

分布式锁

### jdk-lock

jdk锁

## log

系统日志处理相关的模块

## thread-pool

- 线程池，通过配置文件灵活配置线程池的相关信息。
- 减少线程池过多

## utils

工具包

## valid

数据校验

