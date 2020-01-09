package com.qin.ribbon.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RetryRule;
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
     * 自定义规则负载均衡规则
     * @return
     */
    @Bean
    public IRule myRule(){
        // 随机算法
        // return new RandomRule();
        // 轮询算法
        //return new RoundRobinRule();

        return new RetryRule();
    }
}
