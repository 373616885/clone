package com.whale.xinyan.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 新颜探针A 响应实体类
 * @author dengjihai
 * @create 2019-03-06
 **/
@Data
public class XinyanResp<T> implements Serializable {


    private boolean success;

    private String errorCode;

    private String errorMsg;

    private T data;

}
