package com.whale.xinyan.jpa;

import com.whale.xinyan.entity.LwlLoanRadarCurrent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LwlLoanRadarCurrentRepository extends JpaRepository<LwlLoanRadarCurrent, Integer>,
        JpaSpecificationExecutor<LwlLoanRadarCurrent> {

    LwlLoanRadarCurrent findByLoanIdAndUid(Integer loanId, Integer uid);

}
