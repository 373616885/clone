### Hystrix 

**分布式面临的问题-- 服务雪崩**

多个微服务之间调用的时候，假如微服务A调用微服务B，微服务B调用微服务C, 微服务C又调用其他的微服务。如果链路上某个微服务调用响应时间过长或者不可用，对微服务A的调用就会占用越来越多的系统资源，**进而引发系统的奔溃**，这就是 “ 雪崩效应 ”

对于高流量的应用来说，单一的后端依赖可能会导致所有的服务器上的资源在几秒内饱和。比失败更糟的是，这些应用可能导致服务之间的延迟增加，线程和其他系统资源的紧张，从而导致整个系统发生更多的级联故障。这些都表示需要对故障和延迟进行隔离和管理，以便单个依赖关系失败，不能取消整个应用程序和系统。

**Hystrix是什么**

Hystrix 是一个用于处理分布式系统的延迟和容错的开源库，在分布式系统里，许多依赖不可避免的会调用失败，比如超时，异常（死锁和死循环）等，Hytrix 能保证在一个依赖出问题的情况下，不会导致整个服务失败，避免级联故障,已提高分布式的弹性。

“ 断路器 ” 本身是一种开关装置，当某个服务单元发生故障只后，通过断路器的故障监控，向调用反返回一个符号预期的，可处理的备选响应（fallback），而不是长时间的等待或者抛出调用方无法处理的异常，这样就保证了服务调用方的线程不会长时间，不必要地占用，从而避免故障在分布式系统中蔓延，乃至雪崩。

**服务熔断**

当扇出链路的某个微服务不可用或者响应时间太长时，会进行服务降级，进而熔断该节点的服务调用，快速返回“ 错误 ”的响应信息。当检测到该节点微服务调用响应正常后恢复调用链路。在SpringCloud 里通过Hystrix实现的。Hystrix 会监控微服务的调用情况，当失败到一定的阈值，缺省是5秒内20次调用失败就会启动熔断机制。熔断机制的注解是@HystrixCommand。

通俗：一个服务发送异常了（超时），直接熔断，返回 fallback ，而不是一直等待

**服务降级**

服务降级（从整体负荷考虑）整体资源快不够了，忍痛将某些服务先关掉，待渡过难关，再开启回来 -- 服务down掉了，返回信息，（客户端自己实现）虽然服务水平下降，但好歹可用，比直接挂掉要强。

通俗：服务挂了--客户端直接返回 fallback ，虽然服务水平下降，但好歹可用，比直接挂掉要强。



### 使用

首先 hystrix 一般与  openfeign 使用 （在方法上使用都可以--Controller也可以）

引人jar

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

 开启 @EnableHystrix

```java
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.qin.hystrix.service")
@EnableHystrix
public class HystrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixApplication.class, args);
    }

}

```

单独使用 @HystrixCommand

```java
/**
 *  全局设置和这比较会去取最小的，这里设置大于 全局设置 是无效的
 */
@GetMapping("/dept/get/{deptNo}")
@HystrixCommand(fallbackMethod = "error",commandProperties = {
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
})
public Dept getDept(@PathVariable("deptNo") long deptNo) {
    Dept dept = deptService.getDept(deptNo);
    if (Objects.isNull(dept) || Objects.isNull(dept.getDeptNo())) {
        throw new RuntimeException("this deptNo :" + deptNo + " is null");
    }
    return dept;
}

public Dept error(@PathVariable("deptNo") long deptNo) {
    System.out.println("===== " + deptNo + " =====");
    return Dept.builder().dbSource("this deptNo :" + deptNo + " is null").build();
}
```

在 @FeignClient 里使用  

**这里和Feign 切记 DeptServiceHystrix 要纳入Spring 管理 和 开启 feign.hystrix.enabled=true**

```java
// yml  start
feign:
  client:
    config:
      default:  #服务名，填写default为所有服务 --这里就已经覆盖的 请求重试的配置--只重试 get 请求
        connectTimeout: 6000
        readTimeout: 6000
  hystrix:
    # 开启后 fallback 这些才生效
    enabled: true
// yml  end
        
@FeignClient(value = "provider" ,fallback = DeptServiceHystrix.class)
public interface DeptService {

    @RequestMapping(value = "dept/find/all", method = RequestMethod.GET)
    List<Dept> findAll();

    @RequestMapping(value = "dept/get/{deptNo}", method = RequestMethod.GET)
    Dept getDept(@PathVariable("deptNo") long deptNo);

}

@Component
public class DeptServiceHystrix implements DeptService{

    @Override
    public List<Dept> findAll() {
        System.out.println("DeptServiceHystrix: findAll");
        return Lists.newArrayList();
    }

    @Override
    public Dept getDept(long deptNo) {
        System.out.println("DeptServiceHystrix:getDept " + deptNo );
        return Dept.builder().dbSource("DeptServiceHystrix").build();
    }
}
```

### 超时问题

hystrix 默认超时一秒需要修改

default 全局 ，getDept 具体方法 ，@HystrixProperty  这三个超时设置，系统会取最小的

```yml
## @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000"
## default 和 getDept （具体方法） 系统只会取 最小的那个
hystrix:
  command:
    getDept:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 2000
    DeptService#findAll():
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 2000
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 4000
```





