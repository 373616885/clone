package com.qin.feign.web;


import com.qin.api.entry.Dept;
import com.qin.feign.service.DeptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class FeignController {

    private final DeptService deptService;

    @GetMapping("feign/dept/find/all")
    public List<Dept> findAll() {
        return deptService.findAll();
    }


}
