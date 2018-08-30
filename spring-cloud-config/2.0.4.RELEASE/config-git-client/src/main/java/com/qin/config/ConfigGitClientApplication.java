package com.qin.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
public class ConfigGitClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigGitClientApplication.class, args);
	}
}
