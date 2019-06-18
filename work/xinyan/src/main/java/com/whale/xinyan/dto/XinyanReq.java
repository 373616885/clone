package com.whale.xinyan.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 新颜请求参数类
 * @author dengjihai
 * @create 2019-03-06
 **/
@Builder
@Accessors(chain = true)
@Data
public class XinyanReq implements Serializable {

    /** 商户号 ,新颜提供给商户的唯一编号 **/
    private String member_id;

    /** 终端号 ,新颜提供给商户的唯一终端编号 **/
    private String terminal_id;

    /** 加密类型,json **/
    private String data_type;

     /** 由请求参数的集合加密参数，具体参照业务请求参数 **/
    private String data_content;

}
