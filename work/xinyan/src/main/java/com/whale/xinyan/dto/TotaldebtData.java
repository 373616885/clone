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
public class TotaldebtData extends XinyanBussinessResp implements Serializable {

    /**
     * 共债结果详情
     */
    private TotaldeResultDetail result_detail;

}