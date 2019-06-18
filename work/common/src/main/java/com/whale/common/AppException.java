package com.whale.common;

/**
 * 自定义异常
 * @author qinjp
 * @date 2019-05-30
 **/
public class AppException extends RuntimeException {

    private static final long serialVersionUID = 4738244051731004899L;

    public AppException() {
        super();
    }

    public AppException(String message) {
        super(message);
    }

}