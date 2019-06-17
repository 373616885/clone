package com.qin.clond.euruka.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurukaOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurukaOneApplication.class, args);
    }

}
