package com.qin.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;

@RestController
public class JdbcController {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    DataSourceProperties dataSourceProperties;

    @Autowired
    private DataSource dataSource;

    @RequestMapping("/dataSource")
    public String dataSource() {
        System.out.println(dataSource);
        System.out.println(dataSource.getClass().getName());
        System.out.println(dataSourceProperties);
        //执行SQL,输出查到的数据
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<?> resultList = jdbcTemplate.queryForList("select * from stock");
        resultList.forEach(System.out::println);
        return "hello" + dataSource.getClass().getName();
    }

}
