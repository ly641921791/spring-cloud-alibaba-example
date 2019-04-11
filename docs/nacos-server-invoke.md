Nacos 服务调用
-

Spring Cloud Alibaba提供了多种消费方式

- LoadBalancerClient 获取服务实例，手动消费
- RestTemplate 通过容器增强后，使用服务名消费
- WebClient 类似RestTemplate（Spring 5引入的，可理解为reactive版的RestTemplate）
- Feign 通过Feign消费

*LoadBalancerClient*

```java
@RestController
public class ConsumerController {
    
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    
    @GetMapping("/consumer")
    public String consumer(){
		ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-producer");
		String url = serviceInstance.getUri() + "/hello?name=consumer";
		return new RestTemplate().getForObject(url, String.class);
    }
    
}
```

*RestTemplate*

```java
@RestController
public class ConsumerController {
    
    @Autowired
	private RestTemplate restTemplate;
    
    @GetMapping("/consumer")
    public String consumer(){
        return restTemplate.getForObject("http://nacos-producer/hello?name=consumer",String.class);
    }
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
```

*WebClient*

```java
@RestController
public class ConsumerController {
    
    @Autowired
	private WebClient.Builder webClientBuilder;
    
    @GetMapping("/consumer")
    public Mono<String> consumer(){
        return webClientBuilder.build()
	        .get()
	        .uri("http://nacos-producer/hello?name=consumer")
	        .retrieve()
	        .bodyToMono(String.class);
    }
    
    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder(){
        return WebClient.builder();
    }
}
```

*Feign*

引入依赖

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

```java
@RestController
@EnableFeignClients
public class ConsumerController {
    
    @Autowired
	private ProducerClient producerClient;
    
    @GetMapping("/consumer")
    public String consumer(){
        return producerClient.hello("consumer");
    }
    
    @FeignClient("nacos-producer")
    interface ProducerClient {
        
        @GetMapping("/hello")
        String hello(@RequestParam(name="name") String name);
        
    }
}
```