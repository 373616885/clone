package com.qin.zuul.controller;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ErrorHandlerController implements ErrorController {

    /**
     * 出异常后进入该方法，交由下面的方法处理
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public String error(HttpServletRequest request, HttpServletResponse response) {

        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        RequestContext ctx = RequestContext.getCurrentContext();
        ZuulException exception = (ZuulException) ctx.getThrowable();

        if (exception instanceof ZuulException) {
            return  status == 404 ? "访问地址不存在" : exception.getMessage();
        }

        return  status == 404 ? "访问地址不存在" : "内部服务器错误";
    }

}
