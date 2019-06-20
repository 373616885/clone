package com.qin.clond.client.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ClientOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientOneApplication.class, args);
    }

}
