package com.qin.hystrix.config;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestCommandAspect {

    /**
     * 配置HystrixCommandAspect的Bean
     * 然后通过AsyncResult来异步执行调用
     */
    @Bean
    public HystrixCommandAspect hystrixCommandAspect() {
        return new HystrixCommandAspect();
    }
}
