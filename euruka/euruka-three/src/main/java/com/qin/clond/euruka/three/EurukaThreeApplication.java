package com.qin.clond.euruka.three;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurukaThreeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurukaThreeApplication.class, args);
    }

}
