package com.qin.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.HostInfoEnvironmentPostProcessor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Value("${server.port}")
    String port;

    @RequestMapping(value = "/hi")
    public String home(@RequestParam String name) {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("========= clinet =========");
        return "hi " + name + ",i am from port:" + port;
    }

}
