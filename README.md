# ZwConfig 配置中心框架
## 打包部署
> jdk 17 maven 3.6+

```java
# 打包
mvn clean package
# 发布
mvn clean deploy -P release
```

## 实现功能
- [x] 服务者端
  - [x] 维护服务者端配置信息
  - [x] 自动选举主节点机制
- [x] 消费者端
  - [x] 支持 Spring 中 @Value 获取配置的方式
  - [x] 支持 Spring 中 @ConfigurationProperties 获取配置的方式
  - [x] 心跳机制
  - [x] 配置更新时，自动更新消费者端 @Value 和 @ConfigurationProperties 两种方式的配置
- [x] 集成 WmRPC 框架

## 待完善
- [ ] logging.level 配置后，即时生效适配
- [ ] 服务者端
  - [ ] 配置信息缓存到 Map 中，从库新成为主节点后，重新更新从库的 Map
- [ ] 客户端
  - [ ] 从 server 获取到配置信息后，在客户端的本地文件留存一份
- [ ] 使用 DeferredResult 实时感知 Server 端配置信息的版本变化，避免心跳定时 5s 的延迟