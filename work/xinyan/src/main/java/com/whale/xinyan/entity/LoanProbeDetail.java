package com.whale.xinyan.entity;


import com.whale.common.bean.IdEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 
 * 借款_新颜探针A_明细表
 * 
 **/
@Data
@Entity
@Table(name = "lwl_loan_probe_detail")
public class LoanProbeDetail extends IdEntity {


	/**借款_新颜探针AID**/
	@Id
	@Column(name = "lprobe_id")
	private Integer lprobeId;

	/**探查结果编码：1：A(Overdue) 4：U**/
	@Column(name = "result_code")
	private Integer resultCode;

	/**最大逾期金额**/
	@Column(name = "max_overdue_amt")
	private String maxOverdueAmt;

	/**最长逾期天数：区间，格式：dd-dd**/
	@Column(name = "max_overdue_days")
	private String maxOverdueDays;

	/**最近逾期时间：格式：yyyy-MM**/
	@Column(name = "latest_overdue_time")
	private String latestOverdueTime;

	/**当前逾期机构数**/
	@Column(name = "currently_overdue")
	private String currentlyOverdue;

	/**当前履约机构数**/
	@Column(name = "currently_performance")
	private String currentlyPerformance;

	/**异常还款机构数**/
	@Column(name = "acc_exc")
	private String accExc;

	/**睡眠机构数**/
	@Column(name = "acc_sleep")
	private String accSleep;



}
