package com.whale.xinyan.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoanRadarApplyDto implements Serializable {

	private String field1;

	private String field2;

	private String apply_score;

	private String apply_credibility;

	private String query_org_count;

	private String query_finance_count;

	private String query_cash_count;

	private String query_sum_count;

	private String latest_query_time;

	private String latest_one_month;

	private String latest_three_month;

	private String latest_six_month;

}
