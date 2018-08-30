package com.qin.feign.config;

import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FooConfiguration {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

//    public static int connectTimeOutMillis = 12000;//超时时间
//    public static int readTimeOutMillis = 12000;
//    @Bean
//    public Request.Options options() {
//        return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
//    }
    /**
     * 配置了这个就会影响到全局ribbon配置
     *
     ribbon:
         OkToRetryOnAllOperations: false
         SocketTimeout: 1000
         ReadTimeout: 1000
         ConnectTimeout: 1000
         # 同一台实例重试次数，不包括首次调用
         MaxAutoRetries: 1
         # 其他负载均衡实例重试次数，不包括首次server调用
         MaxAutoRetriesNextServer: 1
     */
//    @Bean
//    public Retryer feignRetryer() {
//        return Retryer.NEVER_RETRY;
//    }
}
