/**
 * Copyright 2019 bejson.com
 */
package com.whale.xinyan.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2019-06-05 18:1:16
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class OverdueResultDetail implements Serializable {

    private String debt_amount;
    private String order_count;
    private List<OverdueDetails> details;
    private String member_count;

    public void setDebt_amount(String debt_amount) {
        this.debt_amount = debt_amount;
    }

    public String getDebt_amount() {
        return debt_amount;
    }

    public void setOrder_count(String order_count) {
        this.order_count = order_count;
    }

    public String getOrder_count() {
        return order_count;
    }

    public void setDetails(List<OverdueDetails> details) {
        this.details = details;
    }

    public List<OverdueDetails> getDetails() {
        return details;
    }

    public void setMember_count(String member_count) {
        this.member_count = member_count;
    }

    public String getMember_count() {
        return member_count;
    }

}