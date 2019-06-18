package com.whale.xinyan.entity;

import com.whale.common.bean.IdEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "lwl_loan_radar_current")
public class LwlLoanRadarCurrent extends IdEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "curr_id")
	private Integer currId;

	@Column(name = "loan_id")
	private Integer loanId;

	@Column(name = "uid")
	private Integer uid;

	@Column(name = "loans_credit_limit")
	private String loansCreditLimit;

	@Column(name = "loans_credibility")
	private String loansCredibility;

	@Column(name = "loans_org_count")
	private String loansOrgCount;

	@Column(name = "loans_product_count")
	private String loansProductCount;

	@Column(name = "loans_max_limit")
	private String loansMaxLimit;

	@Column(name = "loans_avg_limit")
	private String loansAvgLimit;

	@Column(name = "consfin_credit_limit")
	private String consfinCreditLimit;

	@Column(name = "consfin_credibility")
	private String consfinCredibility;

	@Column(name = "consfin_org_count")
	private String consfinOrgCount;

	@Column(name = "consfin_product_count")
	private String consfinProductCount;

	@Column(name = "consfin_max_limit")
	private String consfinMaxLimit;

	@Column(name = "consfin_avg_limit")
	private String consfinAvgLimit;

}
