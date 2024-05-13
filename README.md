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
- [x] 消费者端
  - [x] 支持 Spring 中 @Value 获取配置的方式
  - [x] 支持 Spring 中 @ConfigurationProperties 获取配置的方式
  - [x] 心跳机制
  - [x] 配置更新时，自动更新消费者端 @Value 和 @ConfigurationProperties 两种方式的配置

## 待完善
- [ ] 集成 WmRPC 框架
- [ ] 服务者端
  - [ ] 自动选举主节点机制