package com.qin.eureka.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SampleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DiscoveryClient client;

    @GetMapping("/api/get/instances")
    public List<String> getInstances() {
        List<String> ids = client.getServices();

        ids.forEach(item -> {
            List<ServiceInstance> serviceInstances = client.getInstances(item);

            serviceInstances.forEach(instance -> {
                logger.info("serviceId" + instance.getServiceId() + "host:post=" + instance.getHost() + ":" + instance.getPort());
            });

        });
        return ids;
    }

}
