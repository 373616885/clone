package com.whale.xinyan.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author qinjp
 * @date 2019-06-13
 **/
@Setter
@Getter
public class ProbeResultDetail implements Serializable {

    /**
     * 探查结果编码
     * 1：A(Overdue)
     * 4：U
     */
    private Integer result_code;

    /**
     * 当前逾期机构数
     */
    private String currently_overdue;

    /**
     * 最大逾期金额
     */
    private String max_overdue_amt;

    /**
     * 睡眠机构数
     */
    private String acc_sleep;

    /**
     * 当前履约机构数
     */
    private String currently_performance;

    /**
     * 最长逾期天数	区间，格式：dd-dd
     */
    private String max_overdue_days;

    /**
     * 最近逾期时间	格式：yyyy-MM
     */
    private String latest_overdue_time;

    /**
     * 异常还款机构数
     */
    private String acc_exc;

}
