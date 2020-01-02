package com.qin.provider.web;

import com.qin.api.entry.Dept;
import com.qin.provider.mapper.DeptMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProviderController {

    private final DeptMapper deptMapper;

    @GetMapping("/dept/find/all")
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

}
