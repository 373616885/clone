package com.qin.config.client.web;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class SampleController {

    @Value("${name}")  // 从对应的配置中心找到文件并把属性注入到value值中
    private String value;

    @RequestMapping("/hello")
    public String hello() {
        return "hello" + value;
    }


}
