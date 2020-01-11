package com.qin.ribbon.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    //Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端负载均衡的工具。
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    /**
     * 默认情况下：一个应用中只能选择一个负载均衡策略 -- 就是一个 spring 容器只能有一个 IRule 实例
     * ribbon 默认的规则
     * 1. RoundRobinRule 轮询
     * 2. RandomRule 随机算法
     * 3. AvailabilityFilteringRule 先过滤多次访问故障而处于断路器跳闸状态的服务，还有并发的连接数超过阈值的服务，然后对剩余的服务列表按照轮询策略进行访问
     * 4. WeightedResponseTimeRule 根据平均响应时间计算所有的服务的权重，响应时间越快服务权重越大被选中的概率越高，刚启动是如果统计信息不足，则使用RoundRobinRule 策略，等统计信息足够会切换到 WeightedResponseTimeRule
     * 5. RetryRule 先按照RoundRobinRule 策略获取服务，如果获取服务失败则在指定时间内进行重试，获取可用的服务
     * 6. BestAvailableRule 会先过滤由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发最小的服务
     * 7. ZoneAvoidanceRule 默认规则，复合判断server 所在区域的性能和server的可用性选择服务器
     */
    @Bean
    public IRule defaultRule(){
        // 随机算法
        // return new RandomRule();
        // 轮询算法
        return new RoundRobinRule();

        //return new RetryRule();
    }
}
