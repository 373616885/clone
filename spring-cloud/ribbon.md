### Ribbon 

一套客户端软件负载均衡工具

### LB(负载均衡)

集中式LB   -- 提供者和消费者 之间提供独立的LB设施 (硬件-F5 软件 nginx)  ,由该设备负责把访问请求通过某种策略转发给提供方

进程内LB --将LB逻辑集成搭到消费方

### 使用

1. 引人eureka客户端 和 ribbon

   ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
   </dependency>
   
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
   </dependency>
   ```

   

2. 开启eureka客户端

   ```yml
   eureka:
     client:
       serviceUrl:
         defaultZone: http://localhost:9001/eureka/,http://localhost:9002/eureka/,http://localhost:9003/eureka/
     instance:
       # eureka 服务名称的修改
       instance-id: ${spring.cloud.client.ip-address}:${server.port}
       # 显示路径显示IP
       prefer-ip-address: true
       # 每间隔1s，向服务端发送一次心跳，证明自己依然”存活“。
       lease-renewal-interval-in-seconds: 1
       # 告诉服务端，如果我2s之内没有给你发心跳，就代表我“死”了，将我踢出掉。
       lease-expiration-duration-in-seconds: 2
   
   spring:
     application:
       name: ribbon
   ```

   

   ```java
   @EnableEurekaClient
   主启动类
   添加注解 开启eureka客户端   
   ```

3. 在 RestTemplate 实例 添加 @LoadBalanced

   ```java
   //Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端负载均衡的工具。
   @Bean
   @LoadBalanced
   public RestTemplate getRestTemplate() {
       return new RestTemplate();
   }
   ```

4. 使用 -- 服务名需要大写

   ```java
   private static final String url = "http://PROVIDER";
   
   private final RestTemplate restTemplate;
   
   @GetMapping("ribbon/dept/find/all")
   public List<Dept> findAll() {
       return restTemplate.getForObject(url + "/dept/find/all", List.class);
   }
   ```

   

### Ribbon 默认提供了7种算法



