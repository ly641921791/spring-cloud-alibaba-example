Sentinel
- 

*第一步* 创建应用

略

*第二步* 引入依赖

```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
        </dependency>
```

*第三步* 配置仪表盘

```properties
spring.cloud.sentinel.transport.dashboard=localhost:8080
```

*第四步* 启动应用

启动应用，调用接口，访问仪表盘，可以看到接口调用的各项数值

*第五步* 配置流控规则

新增流控规则

阈值类型 QPS
单机阈值 2

每秒最多2个请求

可以测试验证

