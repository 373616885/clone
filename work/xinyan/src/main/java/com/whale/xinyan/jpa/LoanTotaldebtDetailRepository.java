package com.whale.xinyan.jpa;

import com.whale.xinyan.entity.LoanTotaldebtDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface LoanTotaldebtDetailRepository extends JpaRepository<LoanTotaldebtDetail, Integer>, JpaSpecificationExecutor<LoanTotaldebtDetail> {

}
