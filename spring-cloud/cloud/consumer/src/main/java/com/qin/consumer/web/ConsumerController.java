package com.qin.consumer.web;

import com.qin.api.entry.Dept;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@AllArgsConstructor
@RestController
public class ConsumerController {

    private static final String url = "http://localhost:8001";

    private final RestTemplate restTemplate;

    @GetMapping("consumer/dept/find/all")
    public List<Dept> findAll() {
        return restTemplate.getForObject(url + "/dept/find/all", List.class);
    }

}
