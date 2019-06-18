package com.whale.xinyan.entity;

import com.whale.common.bean.IdEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "lwl_loan_radar_apply")
public class LwlLoanRadarApply extends IdEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "apply_id")
	private Integer applyId;

	@Column(name = "loan_id")
	private Integer loanId;

	@Column(name = "uid")
	private Integer uid;

	@Column(name = "apply_score")
	private String applyScore;

	@Column(name = "apply_credibility")
	private String applyCredibility;

	@Column(name = "query_org_count")
	private String queryOrgCount;

	@Column(name = "query_finance_count")
	private String queryFinanceCount;

	@Column(name = "query_cash_count")
	private String queryCashCount;

	@Column(name = "query_sum_count")
	private String querySumCount;

	@Column(name = "latest_query_time")
	private String latestQueryTime;

	@Column(name = "latest_one_month")
	private String latestOneMonth;

	@Column(name = "latest_three_month")
	private String latestThreeMonth;

	@Column(name = "latest_six_month")
	private String latestSixMonth;

}
