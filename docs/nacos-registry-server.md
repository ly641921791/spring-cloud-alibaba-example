Nacos 服务注册
-

*第一步*：创建应用

创建Spring Boot应用

*第二步*：加入依赖

```xml
<project>        
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.5.RELEASE</version>
    </parent>
    
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>0.2.1.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Finchley.SR2</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
    </dependencies>
</project>
``` 

*第三步*：标记注解

启动类标记@EnabledDiscoveryClient注解

```java
@EnabledDiscoveryClient
@SpringBootApplicatioon
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
}
```

*第四步*：修改配置

配置服务名和Nacos服务地址

```property
spring.application.name=nacos-server
spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848
```

*第五步*：启动应用

在Nacos服务列表可以发现刚刚启动的服务

