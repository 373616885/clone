### Hystrix-dash-board

Hystrix 除了隔离依赖服务的调用外，Hystrix 还提供了实时的调用监控（hystrix-dash-board）

Hystrix 会持续记录所有通过Hystrix 发起的请求的执行信息，并以统计报表和图像的形式展示给客户

包括每秒执行多少请求，多少成功，多少失败

Netflix 通过hystrix-metries-event-stream项目实现了对以上指标的监控。

Spring Cloud 也提供Hytrix-dash-board的整合，对监控内容转换成可视乎界面



### 构建 hystrix-dash-board 工程 

1. pom.xml 添加依赖--里面的重要项目  hystrix-core 和  hystrix-metrics-event-stream 

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
</dependency>
```

	2. 在hystrix-dash-board工程里开启监控 @EnableHystrixDashboard

```java
@EnableHystrixDashboard
@SpringBootApplication
public class DashBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(DashBoardApplication.class, args);
    }

}
```

3. 启动 hystrix-dash-board工程 -- 输入url 看到页面证明启动成功

```url
http://localhost:8400/hystrix
```

![](img\20200208200208.png)







4. 被监控的微服务（这里指的是提供微服务的工程 和 hystrix-dash-board 不是一个工程）--需要添加监控配置

   配置完成后可以通过 http://localhost:8301/actuator/hystrix.stream 查看

```xml
（1）pom.xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>

(2) yml 在actuator 将 hystrix.stream 暴露
management:
  endpoints:
    web:
      exposure:
        include: hystrix.stream
    enabled-by-default: false

(3) @EnableHystrix

(4) 添加 HystrixMetricsStreamServlet Servlet 
package com.qin.hystrix.config;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HystrixDashboardConfig {

    @Bean
    public ServletRegistrationBean getServlet(){
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean<HystrixMetricsStreamServlet> registrationBean = new ServletRegistrationBean<HystrixMetricsStreamServlet>(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }

}
```



### 使用

在  http://localhost:8400/hystrix  里面的-输入框-填写你要监控的 actuator/hystrix.stream

  ![](img\20200208203720.png)

Delay : 没 2000 ms 一次轮训

Title :  这次监控的主题 — 顺便定义

点击：Monitor Stream 就会出现画面



![](img\20200208204005.png)



查看：7色 1圈 1 线 

7 色对应 

![](img\20200208205550.png)



1 圈：颜色代表健康程度 绿 < 黄 < 橙 < 红

​		  大小代表流量，流量越大圆圈就越大

1 线：用来记录2分钟内流量的相对变化



![](img\20200208211209.png)



![](img\20200208211243.png)

