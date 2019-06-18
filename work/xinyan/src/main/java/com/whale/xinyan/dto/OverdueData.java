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
public class OverdueData extends XinyanBussinessResp implements Serializable {

    private OverdueResultDetail result_detail;

    public void setResult_detail(OverdueResultDetail result_detail) {
        this.result_detail = result_detail;
    }

    public OverdueResultDetail getResult_detail() {
        return result_detail;
    }

}