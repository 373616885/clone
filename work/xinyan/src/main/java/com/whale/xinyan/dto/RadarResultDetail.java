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
public class RadarResultDetail implements Serializable {

    //申请雷达报告详情
    private LoanRadarApplyDto apply_report_detail;

    //当前逾期机构数
    private LoanRadarBehaviorDto behavior_report_detail;

    //最大逾期金额
    private LoanRadarCurrentDto current_report_detail;


}
