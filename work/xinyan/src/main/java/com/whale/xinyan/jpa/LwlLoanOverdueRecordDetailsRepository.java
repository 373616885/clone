package com.whale.xinyan.jpa;

import com.whale.xinyan.entity.LwlLoanOverdueRecordDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface LwlLoanOverdueRecordDetailsRepository extends JpaRepository<LwlLoanOverdueRecordDetails, Integer>, JpaSpecificationExecutor<LwlLoanOverdueRecordDetails> {


}
