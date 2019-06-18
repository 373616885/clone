package com.whale.xinyan.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author dengjihai
 * @create 2019-03-06
 **/
@Getter
@Setter
public class RadarData extends XinyanBussinessResp implements Serializable {


    /**
     * 核查结果详情	当code=0时有值，其他状态为空
     */
    private RadarResultDetail result_detail;

}
