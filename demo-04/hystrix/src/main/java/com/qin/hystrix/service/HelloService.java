package com.qin.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

@Service
public class HelloService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiError")
    public String hiService(String name) {
        //这里的超时只与RestTemplate有关
        return restTemplate.getForObject("http://CLIENT/hi?name=" + name, String.class);
    }

    public String hiError(String name) {
        return "hi," + name + ",sorry,error!";
    }

    // 异步执行
    @HystrixCommand(fallbackMethod = "error",ignoreExceptions = ArithmeticException.class)
    public Future<String> asyncResult(String name) {
        return new AsyncResult<String>() {
            @Override
            public String invoke() {
                // 配合ignoreExceptions 属性使用
                //int i = 1 / 0;
                return restTemplate.getForObject("http://CLIENT/hi?name=" + name, String.class);
            }
        };
    }

    public String error(String name,Throwable throwable) {
        if(throwable !=null) {
            System.out.println(throwable.getMessage());
        }
        return "AsyncResult hi," + name + ",sorry,error!";
    }

    @CacheResult
    @HystrixCommand(fallbackMethod = "cacheError")
    public String cache01(@CacheKey String name) {
        //这里的超时只与RestTemplate有关
        return restTemplate.getForObject("http://CLIENT/hi?name=" + name, String.class);
    }

    @CacheResult(cacheKeyMethod = "getCacheKey")
    @HystrixCommand(fallbackMethod = "cacheError" )
    public String cache02(String name) {
        //这里的超时只与RestTemplate有关
        return restTemplate.getForObject("http://CLIENT/hi?name=" + name, String.class);
    }

    public String cacheError(String name) {
        return "cacheError ," + name + ",sorry,error!";
    }

    public String getCacheKey(String name){
        return name;
    }

    @CacheResult
    @HystrixCommand(fallbackMethod = "cacheError",
            commandKey = "cache03",
            groupKey = "helloService",
            threadPoolKey = "helloServiceThreadPool")//commandKey 不指定默认方法名
    public String cache03(@CacheKey String name) {
        //这里的超时只与RestTemplate有关
        return restTemplate.getForObject("http://CLIENT/hi?name=" + name, String.class);
    }

    @CacheRemove(commandKey = "cache03")
    @HystrixCommand(commandKey = "cacheRemove",groupKey = "helloService",threadPoolKey = "helloServiceThreadPool")
    public void cacheRemove(@CacheKey String name){
        //restTemplate.postForObject("http://users-service/user",user,UserVO.class);
        System.out.println("成功清楚缓存");
    }

}
