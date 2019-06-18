package com.whale.xinyan.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author dengjihai
 * @create 2019-03-06
 **/
@Builder
@Accessors(chain = true)
@Data
public class XinyanBusinessReq implements Serializable {

    /**
     * 商户号
     **/
    private String member_id;

    /**
     * 终端号
     **/
    private String terminal_id;

    /**
     * 商户请求订单号
     **/
    private String trans_id;

    /**
     * 交易时间 格式：yyyyMMddHHmmss
     **/
    private String trade_date;

    /**
     * 身份证号 MD5(MD5为32位小写)
     **/
    private String id_no;

    /**
     * 姓名 MD5(MD5为32位小写)
     **/
    private String id_name;

    /**
     * 手机号 ，非必填 MD5(MD5为32位小写)
     **/
    private String phone_no;

    /**
     * 银行卡号，非必填  MD5(MD5为32位小写)
     **/
    private String bankcard_no;

    /**
     * 身份信息加密类型 MD5(MD5为32位小写)
     **/
    private String encrypt_type;

    /**
     * 版本号
     **/
    private String versions;


}
