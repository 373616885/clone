/**
 * Copyright 2019 bejson.com
 */
package com.whale.xinyan.dto;

import java.io.Serializable;

/**
 * Auto-generated: 2019-06-06 9:38:53
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class TotaldebtDetail implements Serializable {

    /**
     * 	共债订单数
     */
    private String totaldebt_order_count;

    /**
     *  共债统计时间范围
     */
    private String totaldebt_date;

    /**
     * 共债订单已还款金额
     */
    private String totaldebt_order_amt;

    /**
     * 疑似借新还旧 Y/N
     */
    private String new_or_old;

    /**
     * 共债机构数
     */
    private String totaldebt_org_count;

    /**
     * 共债订单已还款金额
     */
    private String totaldebt_order_lend_amt;

    public void setTotaldebt_order_count(String totaldebt_order_count) {
        this.totaldebt_order_count = totaldebt_order_count;
    }

    public String getTotaldebt_order_count() {
        return totaldebt_order_count;
    }

    public void setTotaldebt_date(String totaldebt_date) {
        this.totaldebt_date = totaldebt_date;
    }

    public String getTotaldebt_date() {
        return totaldebt_date;
    }

    public void setTotaldebt_order_amt(String totaldebt_order_amt) {
        this.totaldebt_order_amt = totaldebt_order_amt;
    }

    public String getTotaldebt_order_amt() {
        return totaldebt_order_amt;
    }

    public void setNew_or_old(String new_or_old) {
        this.new_or_old = new_or_old;
    }

    public String getNew_or_old() {
        return new_or_old;
    }

    public void setTotaldebt_org_count(String totaldebt_org_count) {
        this.totaldebt_org_count = totaldebt_org_count;
    }

    public String getTotaldebt_org_count() {
        return totaldebt_org_count;
    }

    public void setTotaldebt_order_lend_amt(String totaldebt_order_lend_amt) {
        this.totaldebt_order_lend_amt = totaldebt_order_lend_amt;
    }

    public String getTotaldebt_order_lend_amt() {
        return totaldebt_order_lend_amt;
    }

}