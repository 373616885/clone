package com.qin.clond.feign.one.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "client", qualifier = "clientService")
public interface ClientService {

    @RequestMapping(value = "/sample", method = RequestMethod.GET)
    String sample(@RequestParam(value = "name") String name);

}
