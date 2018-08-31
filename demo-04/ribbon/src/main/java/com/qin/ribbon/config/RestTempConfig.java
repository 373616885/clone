package com.qin.ribbon.config;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTempConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
//        return restTemplateBuilder
//                .setConnectTimeout(2000)
//                .setReadTimeout(2000)
//                .build();
//        return new RestTemplate();

        return restTemplateBuilder
                .requestFactory(OkHttp3ClientHttpRequestFactory.class)
                .setConnectTimeout(6000)
                .setReadTimeout(6000)
                .build();

    }

}
