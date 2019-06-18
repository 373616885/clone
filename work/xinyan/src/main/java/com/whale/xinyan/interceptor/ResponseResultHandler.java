package com.whale.xinyan.interceptor;

import com.whale.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Nullable;

/**
 * @author qinjp
 * @date 2019-05-30
 **/
@Slf4j
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(@Nullable MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true ;
    }

    @Override
    public Object beforeBodyWrite(Object body, @Nullable MethodParameter returnType, @Nullable MediaType selectedContentType, @Nullable Class<? extends HttpMessageConverter<?>> selectedConverterType, @Nullable ServerHttpRequest request, @Nullable ServerHttpResponse response) {
        if (body instanceof Result) {
            return body;
        }
        return Result.success(body);
    }
}
