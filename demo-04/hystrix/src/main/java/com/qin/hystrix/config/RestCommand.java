package com.qin.hystrix.config;


import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * 自定义HystrixCommand
 */
public class RestCommand extends HystrixCommand<String> {

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

    public RestCommand() {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("")));
    }

    @Override
    protected String run() throws Exception {
        return restTemplate.getForObject("http://CLIENT/hi?name=" + parm, String.class);
    }


}
