# 核心包

一些都是为了简化,通用.

## boot

启动相关
### moule-builder 

模块构建，扫表子模块用  

## cache

缓存

### local-cache

本地缓存，主要的功能如下：

- 单体机缓存的解决方案，可以设置缓存的过期时间
- 可以设置缓存的淘汰策略（模仿redis--还在实现中）
- 支持持久化（实现中。。。）

## excel

excel操作的模块。技术选型用的easyexcel(poi容易引发oom)

### html-xls

将html转换为xls：

- 支持复杂的合并
- 支持自定义的html样式解析（还没有完善）

## thread-pool

- 线程池，通过配置文件灵活配置线程池的相关信息。
- 减少线程池过多

## valid

数据校验

