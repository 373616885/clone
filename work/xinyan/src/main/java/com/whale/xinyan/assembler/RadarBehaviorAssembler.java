package com.whale.xinyan.assembler;


import com.whale.xinyan.dto.LoanRadarBehaviorDto;
import com.whale.xinyan.entity.LwlLoanRadarBehavior;


/**
 * generate by dengjihai
 */
public class RadarBehaviorAssembler {

    public static LwlLoanRadarBehavior convertToEntity(LoanRadarBehaviorDto loanRadarBehaviorDto, LwlLoanRadarBehavior loanRadarBehavior,Integer uid, Integer
            loanId) {
        if (loanRadarBehavior == null) {
            loanRadarBehavior = new LwlLoanRadarBehavior();
        }
        loanRadarBehavior.setLoanId(loanId);
        loanRadarBehavior.setUid(uid);
        loanRadarBehavior.setLoansScore(loanRadarBehaviorDto.getLoans_score());
        loanRadarBehavior.setLoansCredibility(loanRadarBehaviorDto.getLoans_credibility());
        loanRadarBehavior.setLoansCount(loanRadarBehaviorDto.getLoans_count());
        loanRadarBehavior.setLoansSettleCount(loanRadarBehaviorDto.getLoans_settle_count());
        loanRadarBehavior.setLoansOverdueCount(loanRadarBehaviorDto.getLoans_overdue_count());
        loanRadarBehavior.setLoansOrgCount(loanRadarBehaviorDto.getLoans_org_count());
        loanRadarBehavior.setLoansCashCount(loanRadarBehaviorDto.getLoans_cash_count());
        loanRadarBehavior.setLatestOneMonth(loanRadarBehaviorDto.getLatest_one_month());
        loanRadarBehavior.setLatestThreeMonth(loanRadarBehaviorDto.getLatest_three_month());
        loanRadarBehavior.setLatestSixMonth(loanRadarBehaviorDto.getLatest_six_month());
        loanRadarBehavior.setHistorySucFee(loanRadarBehaviorDto.getHistory_suc_fee());
        loanRadarBehavior.setHistoryFailFee(loanRadarBehaviorDto.getHistory_fail_fee());
        loanRadarBehavior.setLatestOneMonthSuc(loanRadarBehaviorDto.getLatest_one_month_suc());
        loanRadarBehavior.setLatestOneMonthFail(loanRadarBehaviorDto.getLatest_one_month_fail());
        loanRadarBehavior.setLoansLongTime(loanRadarBehaviorDto.getLoans_long_time());
        loanRadarBehavior.setLoansLatestTime(loanRadarBehaviorDto.getLoans_latest_time());
        return loanRadarBehavior;
    }

}
