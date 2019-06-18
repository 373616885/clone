package com.qin.clond.feign.one;

import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

//@Configuration
public class FeginConfig {

//    @Bean
//    public Retryer feignRetryer() {
//        return Retryer.NEVER_RETRY;
//    }

//    @Bean
//    public Request.Options requestOptions(ConfigurableEnvironment env){
//        int ribbonReadTimeout = env.getProperty("ribbon.ReadTimeout", int.class, 16000);
//        int ribbonConnectionTimeout = env.getProperty("ribbon.ConnectTimeout", int.class, 16000);
//
//        return new Request.Options(ribbonConnectionTimeout, ribbonReadTimeout);
//    }

}
