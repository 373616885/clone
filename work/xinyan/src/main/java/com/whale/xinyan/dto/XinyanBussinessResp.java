package com.whale.xinyan.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author dengjihai
 * @create 2019-03-06
 **/
@Getter
@Setter
public class XinyanBussinessResp implements Serializable {

    /**
     * 查询结果码
     * 0：查询成功
     * 1：查询未命中
     * 9：其他异常
     */
    private String code;

    /**
     * 查询结果描述
     */
    private String desc;

    /**
     * 商户请求订单号
     */
    private String trans_id;

    /**
     * 交易流水号
     */
    private String trade_no;

    /**
     * 收费标示
     */
    private String fee;

    /**
     * 身份证号 MD5(MD5为32位小写)
     */
    private String id_no;

    /**
     * MD5(MD5为32位小写)
     */
    private String id_name;

    /**
     * 业务版本号
     */
    private String versions;


}
