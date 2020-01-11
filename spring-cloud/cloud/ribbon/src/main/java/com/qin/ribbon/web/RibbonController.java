package com.qin.ribbon.web;

import com.qin.api.entry.Dept;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@AllArgsConstructor
@RestController
public class RibbonController {

    private static final String PROVIDER = "http://PROVIDER";

    private static final String PROVIDERTEST = "http://PROVIDERTEST";

    private final RestTemplate restTemplate;

    @GetMapping("ribbon/dept/find/all")
    public List<Dept> findAll() {
        return restTemplate.getForObject(PROVIDER + "/dept/find/all", List.class);
    }

    @GetMapping("ribbon/test/dept/find/all")
    public List<Dept> findTestAll() {
        return restTemplate.getForObject(PROVIDERTEST + "/dept/find/all", List.class);
    }

}



