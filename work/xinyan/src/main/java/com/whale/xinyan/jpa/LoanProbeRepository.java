package com.whale.xinyan.jpa;

import com.whale.xinyan.entity.LoanProbe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface LoanProbeRepository extends JpaRepository<LoanProbe, Integer>, JpaSpecificationExecutor<LoanProbe> {



    LoanProbe findByLoanIdAndUid(Integer loanId,Integer uid);


    LoanProbe findByLoanIdAndApiTypeAndUid(Integer loanId,Integer apitype,Integer uid);
}
