## application 文件说明

- template 模板（示例）配置 每次增加配置信息需要只在这个文件提交
- test 测试环境的配置
- local 本地环境（不需要提交）
- pro 生产环境

## bootstrap 文件说明

引导配置文件 当application的配置过多的时候，我们可以适当的将其拆分，例如：

```
##应用名称配置
spring:
  application:
    ##会员服务
    name: message-service
## nacos配置
  cloud:
    nacos:
      config:
        server-addr: ${nacos-host}:8848
        ##配置文件拓展名
        file-extension: yml
        ## 命名空间
        namespace: public
      ## 注册中心配置
      discovery:
        server-addr: ${nacos-host}:8848
        namespace: public
        group: DEFAULT_GROUP
        enabled: true
```

注意：这个配置要利用nacos才有效

