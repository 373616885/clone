package com.qin.ribbon;

import com.qin.rule.MyRuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient

@RibbonClient(name = "PROVIDER" ,configuration = MyRuleConfig.class)
//@RibbonClients(value = {
//        @RibbonClient(name = "PROVIDER",configuration = MyRuleConfig.class),
//        @RibbonClient(name = "PROVIDERTEST",configuration = MyRuleTestConfig.class)
//})
public class RibbonApplication {

    /**
     * @RibbonClient 这个配置 单个 服务的 规则
     * @RibbonClients 这个配置 多个 服务的 规则
     * 注意一点:自定义负载均衡的策略以及算法不能放在@ComponentScan所扫描的包下，
     * 不然会被所有Ribbon所共享(即所有的微服务都会使用该策略，包含PROVIDER)
     * 因为：默认情况一个应用中只能选择一个负载均衡策略 -- 就是一个 spring 容器只能有一个 IRule 实例
     */
    public static void main(String[] args) {
        SpringApplication.run(RibbonApplication.class, args);
    }

}
