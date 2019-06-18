package com.whale.xinyan.jpa;

import com.whale.xinyan.entity.LoanTotaldebt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface LoanTotaldebtRepository extends JpaRepository<LoanTotaldebt, Integer>, JpaSpecificationExecutor<LoanTotaldebt> {

    LoanTotaldebt findByLoanIdAndUid(Integer loanId,Integer uid);
}
