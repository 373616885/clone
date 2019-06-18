package com.whale.xinyan.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoanRadarBehaviorDto implements Serializable {

	private String field1;

	private String field2;

	private String loans_score;

	private String loans_credibility;

	private String loans_count;

	private String loans_settle_count;

	private String loans_overdue_count;

	private String loans_org_count;

	private String consfin_org_count;

	private String loans_cash_count;

	private String latest_one_month;

	private String latest_three_month;

	private String latest_six_month;

	private String history_suc_fee;

	private String history_fail_fee;

	private String latest_one_month_suc;

	private String latest_one_month_fail;

	private String loans_long_time;

	private String loans_latest_time;

}
