package com.whale.xinyan.entity;

import com.whale.common.bean.IdEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * generated by Generate POJOs.groovy
 * <p>Date: Thu Jun 06 11:31:43 CST 2019.</p>
 *
 * @author Chaui
 */
@Data
@Entity
@Table(name = "lwl_loan_totaldebt")
public class LoanTotaldebt extends IdEntity {

    /**
     * 共债档案ID
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "totaldebt_id")
    private Integer totaldebtId;

    /**
     * 借款ID
     */
    @Column(name = "loan_id")
    private Integer loanId;

    /**
     * 用户ID
     */
    @Column(name = "uid")
    private Integer uid;

    /**
     * 身份证号
     */
    @Column(name = "id_no")
    private String idNo;

    /**
     * 姓名
     */
    @Column(name = "id_name")
    private String idName;

    /**
     * 近1月共债机构数
     */
    @Column(name = "current_org_count")
    private String currentOrgCount;

    /**
     * 近1月共债机构数
     */
    @Column(name = "current_order_count")
    private String currentOrderCount;

    /**
     * 近1月共债订单已还款金额
     */
    @Column(name = "current_order_amt")
    private String currentOrderAmt;

    /**
     * 近1月共债订单金额
     */
    @Column(name = "current_order_lend_amt")
    private String currentOrderLendAmt;



}
