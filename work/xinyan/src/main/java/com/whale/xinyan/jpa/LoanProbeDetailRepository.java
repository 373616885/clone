package com.whale.xinyan.jpa;

import com.whale.xinyan.entity.LoanProbeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface LoanProbeDetailRepository extends JpaRepository<LoanProbeDetail, Integer>, JpaSpecificationExecutor<LoanProbeDetail> {

}
