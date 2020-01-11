package com.qin.rule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRuleTestConfig {

    @Bean
    public IRule myRule(){
        // 随机算法
        return new RandomRule();
    }

}
