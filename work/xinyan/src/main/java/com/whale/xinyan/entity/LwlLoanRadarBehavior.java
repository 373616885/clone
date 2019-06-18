package com.whale.xinyan.entity;

import com.whale.common.bean.IdEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "lwl_loan_radar_behavior")
public class LwlLoanRadarBehavior extends IdEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "behavior_id")
	private Integer behaviorId;

	@Column(name = "loan_id")
	private Integer loanId;

	@Column(name = "uid")
	private Integer uid;

	@Column(name = "loans_score")
	private String loansScore;

	@Column(name = "loans_credibility")
	private String loansCredibility;

	@Column(name = "loans_count")
	private String loansCount;

	@Column(name = "loans_settle_count")
	private String loansSettleCount;

	@Column(name = "loans_overdue_count")
	private String loansOverdueCount;

	@Column(name = "loans_org_count")
	private String loansOrgCount;

	@Column(name = "consfin_org_count")
	private String consfinOrgCount;

	@Column(name = "loans_cash_count")
	private String loansCashCount;

	@Column(name = "latest_one_month")
	private String latestOneMonth;

	@Column(name = "latest_three_month")
	private String latestThreeMonth;

	@Column(name = "latest_six_month")
	private String latestSixMonth;

	@Column(name = "history_suc_fee")
	private String historySucFee;

	@Column(name = "history_fail_fee")
	private String historyFailFee;

	@Column(name = "latest_one_month_suc")
	private String latestOneMonthSuc;

	@Column(name = "latest_one_month_fail")
	private String latestOneMonthFail;

	@Column(name = "loans_long_time")
	private String loansLongTime;

	@Column(name = "loans_latest_time")
	private String loansLatestTime;

}
