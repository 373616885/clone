package com.qin.hystrix.config;


import com.netflix.hystrix.*;
import org.springframework.web.client.RestTemplate;

/**
 * 自定义HystrixCommand
 */
public class RestCommandCache extends HystrixCommand<String> {

    private RestTemplate restTemplate;

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private String parm;

    public void setParm(String parm) {
        this.parm = parm;
    }

    @Override
    protected String getFallback() {
        Throwable executionException = getExecutionException();// 获取异常信息
        System.out.println(executionException.getMessage());
        return "Fallback";
    }

    public RestCommandCache() {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("cache")));
    }
    /**
     * 通过 commandKey 清除线程的缓存
     */
    public RestCommandCache(HystrixCommandKey commandKey) {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("cache")).andCommandKey(commandKey));
    }

    @Override
    protected String run() throws Exception {
        return restTemplate.getForObject("http://CLIENT/hi?name=" + parm, String.class);
    }

    /**
     * 缓存策略
     */
    @Override
    protected String getCacheKey() {
        return parm;
    }
}
