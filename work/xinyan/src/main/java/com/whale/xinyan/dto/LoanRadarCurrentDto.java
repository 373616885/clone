package com.whale.xinyan.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoanRadarCurrentDto implements Serializable {

	private String field1;

	private String field2;

	private String loans_credit_limit;

	private String loans_credibility;

	private String loans_org_count;

	private String loans_product_count;

	private String loans_max_limit;

	private String loans_avg_limit;

	private String consfin_credit_limit;

	private String consfin_credibility;

	private String consfin_org_count;

	private String consfin_product_count;

	private String consfin_max_limit;

	private String consfin_avg_limit;



}
