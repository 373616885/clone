package com.qin.clond.client.one;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Value("${server.port}")
    String port;

    @RequestMapping(value = "/sample")
    public String home(@RequestParam String name) {
        System.out.println("========= client Start " + port + "  "+ System.currentTimeMillis() +" =========");
        try {
            Thread.sleep(8000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("========= client End " + port + "  "+ System.currentTimeMillis() +" =========");
        return "hi " + name + ",i am from port:" + port;
    }

}
