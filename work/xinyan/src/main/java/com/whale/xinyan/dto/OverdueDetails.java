/**
 * Copyright 2019 bejson.com
 */
package com.whale.xinyan.dto;

import java.io.Serializable;

/**
 * Auto-generated: 2019-06-05 18:1:16
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class OverdueDetails implements Serializable {

    private String date;
    private String amount;
    private String count;
    private String settlement;

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCount() {
        return count;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    public String getSettlement() {
        return settlement;
    }

}