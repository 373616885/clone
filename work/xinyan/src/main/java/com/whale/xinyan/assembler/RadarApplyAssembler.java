package com.whale.xinyan.assembler;


import com.whale.xinyan.dto.LoanRadarApplyDto;
import com.whale.xinyan.entity.LwlLoanRadarApply;

/**
 * generate by dengjihai
 */
public class RadarApplyAssembler {

    public static LwlLoanRadarApply convertToEntity(LoanRadarApplyDto loanRadarApplyDto, LwlLoanRadarApply loanRadarApply,Integer uid, Integer
            loanId) {
        if (loanRadarApply == null) {
            loanRadarApply = new LwlLoanRadarApply();
        }
        loanRadarApply.setLoanId(loanId);
        loanRadarApply.setUid(uid);
        loanRadarApply.setApplyScore(loanRadarApplyDto.getApply_score());
        loanRadarApply.setApplyCredibility(loanRadarApplyDto.getApply_credibility());
        loanRadarApply.setQueryOrgCount(loanRadarApplyDto.getQuery_org_count());
        loanRadarApply.setQueryFinanceCount(loanRadarApplyDto.getQuery_finance_count());
        loanRadarApply.setQueryCashCount(loanRadarApplyDto.getQuery_cash_count());
        loanRadarApply.setQuerySumCount(loanRadarApplyDto.getQuery_sum_count());
        loanRadarApply.setLatestQueryTime(loanRadarApplyDto.getLatest_query_time());
        loanRadarApply.setLatestOneMonth(loanRadarApplyDto.getLatest_one_month());
        loanRadarApply.setLatestThreeMonth(loanRadarApplyDto.getLatest_three_month());
        loanRadarApply.setLatestSixMonth(loanRadarApplyDto.getLatest_six_month());
        return loanRadarApply;
    }

}
