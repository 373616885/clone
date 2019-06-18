package com.whale.xinyan.jpa;

import com.whale.xinyan.entity.LwlLoanOverdueRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface LwlLoanOverdueRecordRepository extends JpaRepository<LwlLoanOverdueRecord, Integer>, JpaSpecificationExecutor<LwlLoanOverdueRecord> {


    LwlLoanOverdueRecord findByLoanIdAndUid(Integer loanId, Integer uid);
}
