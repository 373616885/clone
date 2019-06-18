package com.whale.xinyan.assembler;


import com.whale.xinyan.dto.LoanRadarCurrentDto;
import com.whale.xinyan.entity.LwlLoanRadarCurrent;


/**
 * generate by dengjihai
 */
public class RadarCurrentAssembler {

    public static LwlLoanRadarCurrent convertToEntity(LoanRadarCurrentDto loanRadarCurrrentDto, LwlLoanRadarCurrent loanRadarCurrent,Integer uid, Integer
            loanId) {
        if (loanRadarCurrent == null) {
            loanRadarCurrent = new LwlLoanRadarCurrent();
        }
        loanRadarCurrent.setLoanId(loanId);
        loanRadarCurrent.setUid(uid);
        loanRadarCurrent.setLoansCreditLimit(loanRadarCurrrentDto.getLoans_credit_limit() );
        loanRadarCurrent.setLoansCredibility(loanRadarCurrrentDto.getLoans_credibility());
        loanRadarCurrent.setLoansOrgCount(loanRadarCurrrentDto.getLoans_org_count());
        loanRadarCurrent.setLoansProductCount(loanRadarCurrrentDto.getLoans_product_count());
        loanRadarCurrent.setLoansMaxLimit(loanRadarCurrrentDto.getLoans_max_limit());
        loanRadarCurrent.setLoansAvgLimit(loanRadarCurrrentDto.getLoans_avg_limit());
        loanRadarCurrent.setConsfinCreditLimit(loanRadarCurrrentDto.getConsfin_credit_limit());
        loanRadarCurrent.setConsfinCredibility(loanRadarCurrrentDto.getConsfin_credibility());
        loanRadarCurrent.setConsfinOrgCount(loanRadarCurrrentDto.getConsfin_org_count());
        loanRadarCurrent.setConsfinProductCount(loanRadarCurrrentDto.getConsfin_product_count());
        loanRadarCurrent.setConsfinMaxLimit(loanRadarCurrrentDto.getConsfin_max_limit());
        loanRadarCurrent.setConsfinAvgLimit(loanRadarCurrrentDto.getConsfin_avg_limit());
        return loanRadarCurrent;
    }

}
