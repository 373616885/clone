package com.qin.hystrix.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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

    /**
     *  全局设置和这比较会去取最小的，这里设置大于 全局设置 是无效的
     */
    @GetMapping("/dept/get/{deptNo}")
    @HystrixCommand(fallbackMethod = "error",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public Dept getDept(@PathVariable("deptNo") long deptNo) {
        Dept dept = deptService.getDept(deptNo);
        if (Objects.isNull(dept) || Objects.isNull(dept.getDeptNo())) {
            throw new RuntimeException("this deptNo :" + deptNo + " is null");
        }
        return dept;
    }

    public Dept error(@PathVariable("deptNo") long deptNo) {
        System.out.println("===== " + deptNo + " =====");
        return Dept.builder().dbSource("this deptNo :" + deptNo + " is null").build();
    }


}
