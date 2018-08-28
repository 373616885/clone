package com.qin.eureka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EurekaApplicationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DiscoveryClient client;

    @Test
    public void contextLoads() {
        List<String> ids = client.getServices();

        ids.forEach(item -> {
            List<ServiceInstance> serviceInstances = client.getInstances(item);

            serviceInstances.forEach(instance -> {
                logger.info("serviceId" + instance.getServiceId() + "host:post=" + instance.getHost() + ":" + instance.getPort());
            });
        });


    }


}
