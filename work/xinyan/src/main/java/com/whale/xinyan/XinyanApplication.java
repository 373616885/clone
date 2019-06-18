package com.whale.xinyan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableEurekaClient
@EnableConfigurationProperties
@EnableJpaRepositories("com.whale.**.jpa")
@ComponentScan(basePackages = "com.whale")
@EntityScan("com.whale.**.entity")
@SpringBootApplication
public class XinyanApplication {

    public static void main(String[] args) {
        SpringApplication.run(XinyanApplication.class, args);
    }

}
