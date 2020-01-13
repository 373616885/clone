package com.qin.hystrix.service;

import com.google.common.collect.Lists;
import com.qin.api.entry.Dept;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeptServiceHystrix implements DeptService{

    @Override
    public List<Dept> findAll() {
        System.out.println("DeptServiceHystrix: findAll");
        return Lists.newArrayList();
    }

    @Override
    public Dept getDept(long deptNo) {
        System.out.println("DeptServiceHystrix:getDept " + deptNo );
        return Dept.builder().dbSource("DeptServiceHystrix").build();
    }
}
