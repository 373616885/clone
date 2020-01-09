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

![](img\20200106223028.png)

1. RoundRobinRule 轮询
2. RandomRule 随机算法
3. AvailabilityFilteringRule 先过滤多次访问故障而处于断路器跳闸状态的服务，还有并发的连接数超过阈值的服务，然后对剩余的服务列表按照轮询策略进行访问
4. WeightedResponseTimeRule 根据平均响应时间计算所有的服务的权重，响应时间越快服务权重越大被选中的概率越高，刚启动是如果统计信息不足，则使用RoundRobinRule 策略，等统计信息足够会切换到 WeightedResponseTimeRule  
5. RetryRule 先按照RoundRobinRule 策略获取服务，如果获取服务失败则在指定时间内进行重试，获取可用的服务
6. BestAvailableRule 会先过滤由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发最小的服务
7. ZoneAvoidanceRule 默认规则，复合判断server 所在区域的性能和server的可用性选择服务器

### 使用Ribbon 规则算法

将 对应的规则纳入Spring 容器里

```java
 	/**
     * 自定义规则负载均衡规则
     * @return
     */
    @Bean
    public IRule myRule(){
        // 随机算法
        // return new RandomRule();
        // 轮询算法
        //return new RoundRobinRule();
		// 先按照RoundRobinRule 策略获取服务，如果获取服务失败则在指定时间内进行重试，获取可用的服务
        return new RetryRule();
    }
```





