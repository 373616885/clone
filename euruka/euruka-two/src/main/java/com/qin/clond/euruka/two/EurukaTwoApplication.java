package com.qin.clond.euruka.two;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurukaTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurukaTwoApplication.class, args);
    }

}
