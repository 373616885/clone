### Feign

Feign 是一个声明式 WebService客户端。使用Feign 能编写WebService客户端更加简单。它的使用是定义一个接口，然后在上面添加注解。Spring cloud 对Feign进行了封装，使其支持Spring mvc 标准注解和HttpMessageConverts。Feign 可以与Eureka 和Ribbon 组合使用以支持负载均衡

通俗：声明式接口加注解进行 WebService服务调用

### 能干什么

旨在使Java Http 客户端变得更加容易

在实际开发中 利用 Ribbon + RestTemplate  进行模板化调用 往往会被多处调用。所以Feign 帮助我们定义和实现依赖服务接口的定义













