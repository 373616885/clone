package com.qin.provider.mapper;


import com.qin.api.entry.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptMapper {

    Dept selectByPrimaryKey(Long deptNo);

    void insertSelective(Dept dept);

    List<Dept> findAll();
}
