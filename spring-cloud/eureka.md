### Eureka 是什么

Eureka 是一个基于REST的服务，用于定位服务，以实现云端中间层服务的发现和故障转移。

Eureka 遵守的AP原则

### CAP

 C（Consistency）强一致性  A（Availability） 可用性  P（Partition tolerance）分区容错性 

 CAP理论就是说在分布式存储系统中，最多只能实现上面的两点 。 而由于当前的网络硬件肯定会出现延迟丢包等问题，所以分区容错性是我们必须要实现的 

###  Eureka 和 Zookeeper区别 

 Zookeeper保证CP原则 ：

​	当向注册中心查询服务列表时，我们可以容忍注册中心返回的是几分钟以前的注册信息，
  但不能接受服务直接down掉不可用。也就是说，服务注册功能对可用性的要求高于一致性。
  但是zk会出现这一种情况，当master节点因为网络故障与其他节点失去联系时，剩余注册
  功能就会重新进行leader选举看。问题在于，选举leader的时间太长，30~120s，且选举期间
  整个zk集群都是不可用的，这就导致在选举期间注册服务瘫痪。在云部署的环境下，因网络问题
  使得zk集群失去master节点是较大概率会发生的事，虽然服务能够最终恢复，但是漫长的
  选举时间导致的注册长期不可用是不能容忍的。

 Eureka是遵守AP原则 ：

Eureka看明白了这一点，因此在设计时就优先保证可用性。Eureka各个节点都是平等
  的，几个节点挂掉不会影响正常节点的工作，剩余节点依然可以提供注册和查询服务。
  而Eureka的客户端在向某个Eureka注册或者如果发现链接失败时，则会自动切换至其他节点，
  只要有一台Eureka还在，就能保证注册服务可用（保证可用），只不过查到的信息可能不是
  最新的（不保证一致性）。

### 使用 

服务端配置文件中加入

```yml
# spring.application.name的优先级比eureka.instance.appname高
spring:
  application:
    name: localhost
eureka:
  instance:
    appname: eureka # eureka服务实例名称
  client:
    fetch-registry: false # 不需要去检索服务，自己就是注册中心
    register-with-eureka: false # false 表示不向注册中心注册自己
    service-url:
      defaultZone: http://${spring.application.name}:${server.port}/eureka/ #对外暴露地址
  server:
    enable-self-preservation: false # 关闭自我保护模式（默认为打开）打开容易取到不存在的服务实例
    eviction-interval-timer-in-ms: 5000 # 续期时间，即扫描失效服务的间隔时间（缺省为60*1000ms）

```

在启动类上加入 @EnableEurekaServer 这个注解

```java
@EnableEurekaServer
@SpringBootApplication
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}
```

客户端配置文件中加入

```yml
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:9001/eureka/
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
    name: provider
    
##  这个里面的是暴露 info 信息配置 start
##  需要加入 spring-boot-starter-actuator 这个jar
info:
  app:
    name: ${spring.application.name}-${management.server.port}
  company:
    name: qinjp
  build:
    artifactId: $project.artifactId$
    version: $project.version$

# 暴露所有端点
management:
  endpoint:
    shutdown:
      # post 请求
      enabled: false
    health:
      show-details: always
  endpoints:
    web:
      #base-path: /application
      exposure:
        # * 在yaml 文件属于关键字
        include: "*"
        exclude: env
  server:
    port: 8006
    servlet:
      context-path: /management # 只有在设置了 management.server.port 时才有效    
##  这个里面的是暴露 info 信息配置 end      
```

在启动类上加入@EnableEurekaClient 这个注解

```java
@EnableEurekaClient
@SpringBootApplication
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}

```



### 集群的使用--相互注册

每个服务段的集群都需要修改服务名--不能冲突

```yml
###  eureka-one start
spring:
  application:
    name: eureka-one
eureka:
  instance:
    appname: eureka-one # eureka服务实例名称
    hostname: one # 主机名 - 不配置的时候将根据操作系统的主机名来获取
    prefer-ip-address: true
    instance-id: ${spring.cloud.client.ip-address}:${server.port}
  client:
    fetch-registry: false # 不需要去检索服务，自己就是注册中心
    register-with-eureka: false # false 表示不向注册中心注册自己
    service-url:
      defaultZone: http://localhost:9002/eureka/,http://localhost:9003/eureka/ #对外暴露地址

  server:
    enable-self-preservation: false # 关闭自我保护模式（默认为打开）打开容易取到不存在的服务实例
    eviction-interval-timer-in-ms: 5000 # 续期时间，即扫描失效服务的间隔时间（缺省为60*1000ms）
 ###  eureka-one end   
    
    
###  eureka-two start
spring:
  application:
    name: eureka-two
eureka:
  instance:
    appname: eureka-two # eureka服务实例名称
    hostname: two # 主机名 - 不配置的时候将根据操作系统的主机名来获取
    prefer-ip-address: true
    instance-id: ${spring.cloud.client.ip-address}:${server.port}
  client:
    fetch-registry: false # 不需要去检索服务，自己就是注册中心
    register-with-eureka: false # false 表示不向注册中心注册自己
    service-url:
      defaultZone: http://localhost:9001/eureka/,http://localhost:9003/eureka/ #对外暴露地址

  server:
    enable-self-preservation: false # 关闭自我保护模式（默认为打开）打开容易取到不存在的服务实例
    eviction-interval-timer-in-ms: 5000 # 续期时间，即扫描失效服务的间隔时间（缺省为60*1000ms）
###  eureka-two end    

###  eureka-three start
spring:
  application:
    name: eureka-three
eureka:
  instance:
    appname: eureka-three # eureka服务实例名称
    hostname: three # 主机名 - 不配置的时候将根据操作系统的主机名来获取
    prefer-ip-address: true
    instance-id: ${spring.cloud.client.ip-address}:${server.port}
  client:
    fetch-registry: false # 不需要去检索服务，自己就是注册中心
    register-with-eureka: false # false 表示不向注册中心注册自己
    service-url:
      defaultZone: http://localhost:9001/eureka/,http://localhost:9002/eureka/ #对外暴露地址

  server:
    enable-self-preservation: false # 关闭自我保护模式（默认为打开）打开容易取到不存在的服务实例
    eviction-interval-timer-in-ms: 5000 # 续期时间，即扫描失效服务的间隔时间（缺省为60*1000ms）
###  eureka-three end
```



客户端：需要注册所有的eureka 服务器

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
    name: provider
```

