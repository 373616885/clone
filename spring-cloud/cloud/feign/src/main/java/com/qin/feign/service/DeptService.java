package com.qin.feign.service;

import com.qin.api.entry.Dept;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "provider")
public interface DeptService {

    @RequestMapping(value = "/dept/find/all", method = RequestMethod.GET)
    List<Dept> findAll();
}
