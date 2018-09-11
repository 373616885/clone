package com.qin.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @SpringCloudApplication
 * 这个注解等于下面三个之和
 */
@SpringCloudApplication
//@EnableEurekaClient
//@EnableHystrix
//@SpringBootApplication(scanBasePackages = {"com.qin.hystrix", "com.qin.common"})
public class HystrixApplication {

	public static void main(String[] args) {
		SpringApplication.run(HystrixApplication.class, args);
	}
}
