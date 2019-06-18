package com.whale.xinyan.jpa;

import com.whale.xinyan.entity.LwlLoanRadarBehavior;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LwlLoanRadarBehaviorRepository extends JpaRepository<LwlLoanRadarBehavior, Integer>,
        JpaSpecificationExecutor<LwlLoanRadarBehavior> {

    LwlLoanRadarBehavior findByLoanIdAndUid(Integer loanId, Integer uid);
}
