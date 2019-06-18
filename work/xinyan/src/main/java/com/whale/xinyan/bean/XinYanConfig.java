package com.whale.xinyan.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author dengjihai
 * @create 2019-03-06
 **/
@Data
@Component
@ConfigurationProperties(prefix="spring.xinyan")
public class XinYanConfig {

    private String host;

    private String merchNo;

    private String pfxPwd;

    private String pfxPath;

    private String cerPath;


}
