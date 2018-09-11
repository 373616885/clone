package com.qin.hystrix.controller;

import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.qin.hystrix.config.RestCommand;
import com.qin.hystrix.config.RestCommandCache;
import com.qin.hystrix.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class HelloControler {

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    HelloService helloService;

    @GetMapping(value = "/hi", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String hi(@RequestParam String name) {
        String result = helloService.hiService(name);
        System.out.println("hystrix: " + result);
        return result;
    }

    @GetMapping(value = "/command", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String command(@RequestParam String name) throws ExecutionException, InterruptedException {
        RestCommand restCommand = new RestCommand();
        restCommand.setParm(name);
        restCommand.setRestTemplate(restTemplate);
        //同步调用
        //String result = restCommand.execute();
        //异步调用
        Future<String> queue = restCommand.queue();
        String result = queue.get();
        System.out.println("hystrix: " + result);
        return result;
    }

    @GetMapping(value = "/cache", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String cache(@RequestParam String name) throws ExecutionException, InterruptedException {
        // 便于清除缓存
        HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey("commandKey");

        //初始化context
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        // 在不同context中的缓存是不共享的，还有这个request内部一个ThreadLocal，所以request只能限于当前线程
        // 这个对象只能执行一次下次执行必须再次new一个对象
        RestCommandCache cacheCommand = new RestCommandCache(commandKey);
        cacheCommand.setParm(name);
        cacheCommand.setRestTemplate(restTemplate);
        //同步调用
        String result = cacheCommand.execute();
        System.out.println("one: " + result);
        // 清除缓存
        //HystrixRequestCache.getInstance(commandKey, HystrixConcurrencyStrategyDefault.getInstance()).clear(name);

        RestCommandCache cacheCommand2 = new RestCommandCache(commandKey);
        cacheCommand2.setParm(name);
        cacheCommand2.setRestTemplate(restTemplate);
        //异步调用
        Future<String> queue = cacheCommand2.queue();
        result = queue.get();
        System.out.println("two: " + result);
        return result;
    }



    @GetMapping(value = "/asyncResult", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String asyncResult(@RequestParam String name) throws ExecutionException, InterruptedException {
        Future<String> result = helloService.asyncResult(name);
        System.out.println("hystrix: " + result.get());
        return result.get();
    }

    @GetMapping(value = "/CacheResult", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String cacheResult(@RequestParam String name) throws ExecutionException, InterruptedException {

        //初始化context
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        // 在不同context中的缓存是不共享的，还有这个request内部一个ThreadLocal，所以request只能限于当前线程
        String result = helloService.cache01(name);
        System.out.println("CacheResult01 one: " + result);
        result = helloService.cache01(name);
        System.out.println("CacheResult01 two: " + result);
        return result;
    }

    @GetMapping(value = "/CacheResult02", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String cacheResult02(@RequestParam String name) throws ExecutionException, InterruptedException {

        //初始化context
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        // 在不同context中的缓存是不共享的，还有这个request内部一个ThreadLocal，所以request只能限于当前线程
        String result = helloService.cache02(name);
        System.out.println("CacheResult02 one: " + result);
        result = helloService.cache02(name);
        System.out.println("CacheResult02 two: " + result);
        return result;
    }

    @GetMapping(value = "/CacheResult03", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String cacheResult03(@RequestParam String name) throws ExecutionException, InterruptedException {

        //初始化context
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        // 在不同context中的缓存是不共享的，还有这个request内部一个ThreadLocal，所以request只能限于当前线程
        String result = helloService.cache03(name);
        System.out.println("CacheResult03 one: " + result);
        result = helloService.cache03(name);
        System.out.println("CacheResult03 two: " + result);
        // 清楚缓存
        helloService.cacheRemove(name);

        result = helloService.cache03(name);
        System.out.println("CacheResult03 three: " + result);
        return result;
    }


}
