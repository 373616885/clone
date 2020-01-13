package com.qin.provider.web;

import com.qin.api.entry.Dept;
import com.qin.provider.mapper.DeptMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProviderController {

    @NonNull
    private final DeptMapper deptMapper;

    @Value("${server.port}")
    private String port;

    @GetMapping("/dept/find/all")
    public List<Dept> findAll() {
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("server.port:" + port);
        return deptMapper.findAll().stream().map(dept -> {
            return dept.setDbSource(port);
        }).collect(Collectors.toList());
    }

    @GetMapping("/dept/get/{deptNo}")
    public Dept getDept(@PathVariable("deptNo") long deptNo) throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("deptNo : " + deptNo + " server.port:" + port);
        Dept dept = deptMapper.selectByPrimaryKey(deptNo);
        return Objects.isNull(dept) ?
                Dept.builder().dbSource("this deptNo is null").build() :
                dept.setDbSource(port);
    }

}
