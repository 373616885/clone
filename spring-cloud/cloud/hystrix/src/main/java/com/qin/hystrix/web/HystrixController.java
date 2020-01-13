package com.qin.hystrix.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.qin.api.entry.Dept;
import com.qin.hystrix.service.DeptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
public class HystrixController {

    private final DeptService deptService;

    @GetMapping("feign/dept/find/all")
    public List<Dept> findAll() {
        return deptService.findAll();
    }

    @GetMapping("/dept/get/{deptNo}")
    @HystrixCommand(fallbackMethod = "error")
    public Dept getDept(@PathVariable("deptNo") long deptNo) {
        Dept dept = deptService.getDept(deptNo);
        if (Objects.isNull(dept)) {
            throw new RuntimeException("this deptNo :" + deptNo + " is null");
        }
        return deptService.getDept(deptNo);
    }

    public Dept error(@PathVariable("deptNo") long deptNo) {
        return Dept.builder().dbSource("this deptNo :" + deptNo + " is null").build();
    }


}
