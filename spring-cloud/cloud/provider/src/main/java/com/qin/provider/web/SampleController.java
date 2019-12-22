package com.qin.provider.web;

import com.qin.api.entry.Dept;
import com.qin.provider.mapper.DeptMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class SampleController {

    private final DeptMapper deptMapper;

    @GetMapping("/sample")
    public List<Dept> sample(){
        Dept dept = Dept.builder().deptName("开发部").dbSource("1").build();

        deptMapper.insertSelective(dept);

        return deptMapper.findAll();
    }

}
