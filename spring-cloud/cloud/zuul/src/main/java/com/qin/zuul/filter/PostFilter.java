package com.qin.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.nio.charset.Charset;

@Slf4j
@Component
public class PostFilter extends ZuulFilter {

    @Override
    public String filterType() {
        //后置过滤器
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        // 上一个过滤器已经拒绝了就不要往下执行
        if (!ctx.sendZuulResponse()) {
            return false;
        }

        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        log.info("进入post过滤器");
        // 获取返回值内容，加以处理
        InputStream stream = ctx.getResponseDataStream();
        try  {
            String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
            System.out.println(body);

            //String returnStr = body;
            //你的处理逻辑，加密，添加新的返回值等等.....

            // 内容重新写入
            ctx.setResponseBody(body);
            // 防止乱码问题
            ctx.getResponse().setContentType("text/html;charset=UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }


//        System.out.println(ctx.getResponseBody());
//
//        ctx.setResponseBody("post后置数据");

        return null;
    }
}
