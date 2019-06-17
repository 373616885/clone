package com.qin.clond.feign.one.web;


import com.qin.clond.feign.one.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Autowired
    @Qualifier("clientService")
    private ClientService clientService;


    @RequestMapping(value = "/sample")
    public String home(@RequestParam String name) {
        clientService.sample(name);
        return clientService.sample(name);
    }
}
