package com.qin.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ErrorFilter extends ZuulFilter {


    @Override
    public String filterType() {
        // 异常过滤器
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        // 顺序级别
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 判断是否被过滤
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();

        log.info("进入异常过滤器");

        System.out.println(ctx.getResponseBody());

        ctx.setResponseBody("出现异常");

        return null;
    }
}
