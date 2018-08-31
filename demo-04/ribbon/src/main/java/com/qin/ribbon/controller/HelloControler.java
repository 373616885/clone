package com.qin.ribbon.controller;

import com.qin.ribbon.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloControler {

    @Autowired
    HelloService helloService;

    @GetMapping(value = "/hi", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String hi(@RequestParam String name) {
        String result = helloService.hiService(name);
        System.out.println("ribbon: " + result);
        return result;
    }
}
