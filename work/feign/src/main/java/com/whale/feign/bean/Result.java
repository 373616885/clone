package com.whale.feign.bean;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author qinjp
 * @date 2019-05-30
 **/
@Data
@Builder
@Accessors(chain = true)
public class Result {

    private boolean success;

    private String msg;

    private Object data;

    public static Result success() {
        return Result.builder().success(true).build();
    }

    public static Result success(Object data) {
        return Result.success().setData(data);
    }

    public static Result fail() {
        return Result.builder().success(false).build();
    }

    public static Result fail(Object data) {
        return Result.fail().setData(data);
    }


}
