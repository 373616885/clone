package com.qin.provider.web;

import lombok.AllArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class DiscoveryController {

    private final DiscoveryClient discoveryClient;

    @GetMapping("/discovery")
    public List<ServiceInstance> discovery(){
        List<String> services = discoveryClient.getServices();

        services.forEach(System.out::println);

        List<ServiceInstance> instances = discoveryClient.getInstances("provider");

        instances.forEach(serviceInstance -> {
            System.out.println(serviceInstance.getServiceId());
            System.out.println(serviceInstance.getInstanceId());
            System.out.println(serviceInstance.getHost());
            System.out.println(serviceInstance.getPort());
            System.out.println(serviceInstance.getUri());
        });

        return instances;
    }

}
