package com.qin.feign.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleContoller {

    @RequestMapping(value = "/hello")
    public String hello() {
        return "hello feign";
    }
}
