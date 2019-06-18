package com.whale.xinyan.entity;


import com.whale.common.bean.IdEntity;
import lombok.Data;

import javax.persistence.*;


/**
 * 
 * 借款_新颜探针A表
 * 
 **/
@Data
@Entity
@Table(name = "lwl_loan_probe")
public class LoanProbe extends IdEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	/**借款_新颜探针AID**/
	@Column(name = "lprobe_id")
	private Integer lprobeId;

	/**借款ID**/
	@Column(name = "loan_id")
	private Integer loanId;

	/**用户ID**/
	@Column(name = "uid")
	private Integer uid;

	/**查询结果码：0：查询成功，1：查询未命中，9：其他异常**/
	@Column(name = "code")
	private Integer code;

	/**查询结果描述**/
	@Column(name = "description")
	private String description;

	/**商户请求订单号**/
	@Column(name = "trans_id")
	private String transId;

	/**新颜交易响应流水号**/
	@Column(name = "trade_no")
	private String tradeNo;

	/**收费标示：Y：收费，N：不收费**/
	@Column(name = "fee")
	private String fee;

	/**新颜API接口类别**/
	@Column(name = "api_type")
	private Integer apiType;

	/**身份证号**/
	@Column(name = "id_no")
	private String idNo;

	/**姓名**/
	@Column(name = "id_name")
	private String idName;

	/**版本号**/
	@Column(name = "versions")
	private String versions;



}
