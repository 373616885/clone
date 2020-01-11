### Feign

Feign 是一个声明式 WebService客户端。使用Feign 能编写WebService客户端更加简单。它的使用是定义一个接口，然后在上面添加注解。Spring cloud 对Feign进行了封装，使其支持Spring mvc 标准注解和HttpMessageConverts。Feign 可以与Eureka 和Ribbon 组合使用以支持负载均衡

通俗：声明式接口加注解进行 WebService服务调用

### 能干什么

旨在使Java Http 客户端变得更加容易

在实际开发中 利用 Ribbon + RestTemplate  进行模板化调用 往往会被多处调用。所以Feign 帮助我们定义和实现依赖服务接口的定义

### 使用

 1.开启 Eureka 客户端 @EnableEurekaClient

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
    name: feign    
```

```java
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class FeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignApplication.class, args);
    }

}
```

2. 编写FeignClient 接口

```java
@FeignClient(value = "provider")
public interface DeptService {

    @RequestMapping(value = "/dept/find/all", method = RequestMethod.GET)
    List<Dept> findAll();
}
```

3. 开启 @EnableFeignClients

```java
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class FeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignApplication.class, args);
    }

}
```

4. http 请求超时问题 

```yml
feign:
  client:
    config:
      default:  #服务名，填写default为所有服务 --这里就已经覆盖的 请求重试的配置--只重试 get 请求
        connectTimeout: 3000
        readTimeout: 3000
```



