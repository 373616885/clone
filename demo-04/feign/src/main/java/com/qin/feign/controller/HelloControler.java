package com.qin.feign.controller;

import com.qin.feign.service.SchedualServiceHi;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.print.attribute.standard.Media;

@RestController
public class HelloControler {

    @Resource(name = "hi")
    private SchedualServiceHi schedualServiceHi;

    @GetMapping(value = "/hi", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String hi(@RequestParam String name) {
        String result = schedualServiceHi.sayHiFromClientOne(name);
        System.out.println("feign: " + result);
        return result;
    }
}
