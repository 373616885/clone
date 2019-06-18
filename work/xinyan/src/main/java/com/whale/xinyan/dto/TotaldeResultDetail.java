/**
 * Copyright 2019 bejson.com
 */
package com.whale.xinyan.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2019-06-06 9:38:53
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class TotaldeResultDetail implements Serializable {
    /**
     * 近1月共债机构数
     */
    private String current_order_count;

    /**
     * 近1月共债订单数
     */
    private String current_org_count;

    /**
     * 近1月共债订单已还款金额
     */
    private String current_order_amt;

    /**
     * 近1月共债订单金额
     */
    private String current_order_lend_amt;

    /**
     * 历史共债
     */
    private List<TotaldebtDetail> totaldebt_detail;

    public void setCurrent_order_count(String current_order_count) {
        this.current_order_count = current_order_count;
    }

    public String getCurrent_order_count() {
        return current_order_count;
    }

    public void setCurrent_org_count(String current_org_count) {
        this.current_org_count = current_org_count;
    }

    public String getCurrent_org_count() {
        return current_org_count;
    }

    public void setCurrent_order_amt(String current_order_amt) {
        this.current_order_amt = current_order_amt;
    }

    public String getCurrent_order_amt() {
        return current_order_amt;
    }

    public void setCurrent_order_lend_amt(String current_order_lend_amt) {
        this.current_order_lend_amt = current_order_lend_amt;
    }

    public String getCurrent_order_lend_amt() {
        return current_order_lend_amt;
    }

    public List<TotaldebtDetail> getTotaldebt_detail() {
        return totaldebt_detail;
    }

    public void setTotaldebt_detail(List<TotaldebtDetail> totaldebt_detail) {
        this.totaldebt_detail = totaldebt_detail;
    }
}