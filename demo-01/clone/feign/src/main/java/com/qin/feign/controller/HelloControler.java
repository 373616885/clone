package com.qin.feign.controller;

import com.qin.feign.service.SchedualServiceHi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloControler {

    @Resource(name = "hi")
    private SchedualServiceHi schedualServiceHi;

    @GetMapping(value = "/hi")
    public String hi(@RequestParam String name) {
        String result = schedualServiceHi.sayHiFromClientOne(name);
        System.out.println(result);
        return result;
    }
}
