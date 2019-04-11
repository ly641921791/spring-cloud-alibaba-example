Nacos 配置中心
-

### 快速开始

*第一步* 创建配置

点击配置列表页的加号按钮，新建配置

Data ID ： nacos-config.properties
Group ： DEFAULT_GROUP
配置格式 ： Properties
配置内容 ： application.dynamic.config=config1

*第二步* 创建应用

略

*第三步* 加入依赖

Nacos的配置中心功能和注册中心功能可以独立使用，此处只加入配置中心功能

```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-alibaba-nacos-config</artifactId>
        </dependency>
    </dependencies>
```

*第四步* 加入配置

创建bootstrap.properties文件，配置服务名和Nacos服务地址，服务名与配置的Data ID相同。文件名必须是bootstrap

```properties
server.port=8080
spring.application.name=nacos-config
spring.cloud.nacos.config.server-addr=127.0.0.1:8848
```

*第五步* 新建服务

```java
@RefreshScope
@RestController
public class DynamicConfig {

    @Value("${application.dynamic.config}")
    private String config;

    @GetMapping("/dynamic")
    public String dynamic(){
        return config;
    }

}
```

@RefreshScope表示该类中的配置需要动态刷新，访问该接口，修改配置后再访问，响应不同，说明配置动态更新。

### 配置详解

- Data ID

对应的配置是${spring.cloud.nacos.config.prefix}.${spring.cloud.nacos.config.file-extension}，

spring.cloud.nacos.config.prefix属性的默认值是${spring.application.name}，

spring.cloud.nacos.config.file-extension属性的默认值是properties

- Group

对应的配置是spring.cloud.nacos.config.group，默认值是DEFAULT_GROUP

### 多环境配置

- 通过Data Id和profiles实现多环境配置

- 通过Group实现多环境配置

- 通过命名空间实现多环境配置

*第一步* 创建命名空间

在控制台命名空间页面新建命名空间，会生成命名空间ID

*第二步* 查看命名空间

在配置列表和服务列表页面上方都会出现新创建的命名空间tab页

*第三步* 使用命名空间

将第一步生成的ID配置在spring.cloud.nacos.config.namespace

### 加载多配置

公共配置抽离后，需要加载多个配置

```
spring.cloud.nacos.config.ext-config[0].data-id=actuator.properties
spring.cloud.nacos.config.ext-config[0].group=DEFAULT_GROUP
spring.cloud.nacos.config.ext-config[0].refresh=true
spring.cloud.nacos.config.ext-config[1].data-id=log.properties
spring.cloud.nacos.config.ext-config[1].group=DEFAULT_GROUP
spring.cloud.nacos.config.ext-config[1].refresh=true
```

默认配置会自动刷新，拓展配置不会，refresh属性表示拓展配置自动刷新

共享配置：略

加载优先级。默认配置 > 拓展配置 > 共享配置

### 数据持久化

默认情况下，Nacos使用嵌入式数据库存储数据，可以修改为MySQL数据库

*第一步* 安装MySQL

版本5.6.5+

*第二步* 初始化数据

在Nacos的conf下找到nacos-mysql.sql文件，执行

*第三步* 配置数据库

修改conf/application.properties文件

```java
spring.datasource.platform=mysql

db.num=1
db.url.0=jdbc:mysql://localhost:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
db.user=root
db.password=
```

### 集群搭建

conf目录下cluster.conf.example文件复制为cluster.conf，配置全部节点

```
127.0.0.1:8848
127.0.0.1:8849
127.0.0.1:8850
```

然后通过代理转发请求，如nginx