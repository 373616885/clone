**Zuul** 

zuul 主要包含两个功能：路由和过滤

路由：将请求转发都对应的微服务上

过滤：将一些请求进行过滤

**使用**

1 .pom.xml 添加jar

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
</dependency>
<!-- 
一般zuul 需要与eureka 进行整合 
使用需要 加入 eureka 客户端
-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

2 .  开启 Eureka 客户端 启动类添加 @EnableEurekaClient 和 yml配置 eureka 客户端

```yml
spring:
  application:
    name: zuul


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
```

3.  启动类开启Zuull 启动类添加 @EnableZuulProxy 和yml 配置 Zuul

   需要注意 ignored-services: "*" 和 超时问题

```yml

zuul:
  # 统一前缀
  prefix: /qinjp
  # zuul 默认是可以使用 服务名来访问的 所以这个需要注意配置
  # 忽略直接使用服务名来访问 -- （ 不能使用 路径 /provider 访问）
  # 忽略所有就使用 "*"  必须有双引号
  ignored-services: "*"
  # ignored-services: provider
  routes:
    ribbon:
      path: /api-provider/**
      serviceId: provider


# zuul 网管超时问题
ribbon:
  OkToRetryOnAllOperations: false
  ReadTimeout: 6000
  ConnectTimeout: 6000
  SocketTimeout: 6000
  # 同一台实例重试次数，不包括首次调用
  MaxAutoRetries: 0
  # 其他负载均衡实例重试次数，不包括首次server调用
  MaxAutoRetriesNextServer: 0
```



### 总结

在yml 配置 Zuul 和 Eureka的 yml   启动类开启对应的服务 （@EnableEurekaClient  @EnableZuulProxy ）

下面是完整的yml 

需要注意zuul默认是可以通过服务名来访问的 所以  ignored-services: "*" 这个必须配置上

不然就可以通过服务名来访问了

因为不配置可以直接通过服务名访问

```yml
server:
  port: 8401

logging:
  config: classpath:log4j2.xml


spring:
  application:
    name: zuul


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


info:
  app:
    name: ${spring.application.name}-${server.port}
  company:
    name: qinjp
  build:
    artifactId: $project.artifactId$
    version: $project.version$


zuul:
  # 统一前缀
  prefix: /qinjp
  # zuul 默认是可以使用 服务名来访问的 所以这个需要注意配置
  # 忽略直接使用服务名来访问 -- （ 不能使用 路径 /provider 访问）
  # 忽略所有就使用 "*"  必须有双引号
  ignored-services: "*"
  # ignored-services: provider
  routes:
    ribbon:
      path: /api-provider/**
      serviceId: provider


# zuul 网管超时问题
ribbon:
  OkToRetryOnAllOperations: false
  ReadTimeout: 6000
  ConnectTimeout: 6000
  SocketTimeout: 6000
  # 同一台实例重试次数，不包括首次调用
  MaxAutoRetries: 0
  # 其他负载均衡实例重试次数，不包括首次server调用
  MaxAutoRetriesNextServer: 0
```

下面是完整的启动类

```java
package com.qin.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

}
```



### zuul 异常处理

覆盖 spring 的 ErrorController 

```java
package com.qin.zuul.controller;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ErrorHandlerController implements ErrorController {

    /**
     * 出异常后进入该方法，交由下面的方法处理
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public String error(HttpServletRequest request, HttpServletResponse response) {

        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        RequestContext ctx = RequestContext.getCurrentContext();
        ZuulException exception = (ZuulException) ctx.getThrowable();

        if (exception instanceof ZuulException) {
            return  status == 404 ? "访问地址不存在" : exception.getMessage();
        }

        return  status == 404 ? "访问地址不存在" : "内部服务器错误";
    }

}

```



### zuul 的过滤器3种过滤器

异常过滤器

```java
package com.qin.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ErrorFilter extends ZuulFilter {


    @Override
    public String filterType() {
        // 异常过滤器
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        // 顺序级别
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 判断是否被过滤
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();

        log.info("进入异常过滤器");

        System.out.println(ctx.getResponseBody());

        ctx.setResponseBody("出现异常");

        return null;
    }
}

```

后置过滤器

```java
package com.qin.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.nio.charset.Charset;

@Slf4j
@Component
public class PostFilter extends ZuulFilter {

    @Override
    public String filterType() {
        //后置过滤器
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        // 上一个过滤器已经拒绝了就不要往下执行
        if (!ctx.sendZuulResponse()) {
            return false;
        }

        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        log.info("进入post过滤器");
        // 获取返回值内容，加以处理
        InputStream stream = ctx.getResponseDataStream();
        try  {
            String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
            System.out.println(body);

            //String returnStr = body;
            //你的处理逻辑，加密，添加新的返回值等等.....

            // 内容重新写入
            ctx.setResponseBody(body);
            // 防止乱码问题
            ctx.getResponse().setContentType("text/html;charset=UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }


//        System.out.println(ctx.getResponseBody());
//
//        ctx.setResponseBody("post后置数据");

        return null;
    }
}

```

前置过滤器

```java
package com.qin.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class PreFilter extends ZuulFilter {

    @Override
    public String filterType() {
        //前置过滤器
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        // 上一个过滤器已经拒绝了就不要往下执行
        if (!ctx.sendZuulResponse()) {
            return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());

        //获取传来的参数accessToken
        Object accessToken = request.getParameter("accessToken");
        if(accessToken == null) {

            log.warn("access token is empty");
            //过滤该请求，不往下级服务去转发请求，到此结束
            ctx.setSendZuulResponse(false);

            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("{\"result\":\"accessToken为空!\"}");
            ctx.getResponse().setContentType("text/html;charset=UTF-8");
            return null;
        }
        //如果有token，则进行路由转发
        log.info("access token ok");
        //这里return的值没有意义，zuul框架没有使用该返回值


        return null;
    }
}

```

