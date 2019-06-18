package com.whale.xinyan.jpa;

import com.whale.xinyan.entity.LwlLoanRadarApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LwlLoanRadarApplyRepository extends JpaRepository<LwlLoanRadarApply, Integer>,
        JpaSpecificationExecutor<LwlLoanRadarApply> {

    LwlLoanRadarApply findByLoanIdAndUid(Integer loanId, Integer uid);
}

