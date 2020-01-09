package com.qin.provider.web;

import com.qin.api.entry.Dept;
import com.qin.provider.mapper.DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProviderController {

    @Autowired
    private DeptMapper deptMapper;

    @Value("${server.port}")
    private String port;

    @GetMapping("/dept/find/all")
    public List<Dept> findAll() {
        System.out.println("server.port:" + port);
        return deptMapper.findAll().stream().map(dept -> {
            return dept.setDbSource(port);
        }).collect(Collectors.toList());
    }

}
